package com.projects.cdharini.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dharinic on 9/13/17.
 */

public class MovieData {

    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;

    public MovieData(JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
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
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }
}
