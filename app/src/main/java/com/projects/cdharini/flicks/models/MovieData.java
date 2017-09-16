package com.projects.cdharini.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dharinic on 9/13/17.
 */

public class MovieData {

    public enum Popularity {
        RIPE,
        ROTTEN
    }

    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;
    String releaseDate;
    Double vote_average;
    Popularity popularity;
    static final Double POPULARITY_MIN_VOTE = 6.0;

    public MovieData(JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.releaseDate = jsonObject.getString("release_date");
        this.vote_average = jsonObject.getDouble("vote_average");
        popularity = (vote_average > POPULARITY_MIN_VOTE) ? Popularity.RIPE : Popularity.ROTTEN;
    }

    public static ArrayList<MovieData> fromJSONArray(JSONArray array) {
        ArrayList<MovieData> movieResults = new ArrayList<MovieData>();
        for (int i = 0; i < array.length(); i++) {
            try {
                movieResults.add(new MovieData(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movieResults;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w300/%s", backdropPath);
    }

    public String getFullBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/original/%s", backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Popularity getPopularity() {
        return popularity;
    }
}
