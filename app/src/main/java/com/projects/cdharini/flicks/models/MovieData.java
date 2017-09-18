package com.projects.cdharini.flicks.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class modelling movie data
 * Created by dharinic on 9/13/17.
 */

public class MovieData implements Parcelable{

    public enum Popularity {
        RIPE,
        ROTTEN
    }

    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;
    private String releaseDate;
    private Double voteAverage;
    private int movieId;
    private Popularity popularity;

    //Threshold to indicate if movie is ripe or rotten
    private static final Double POPULARITY_MIN_VOTE = 6.0;

    //Scaling factor to display rating out of 5 stars
    public static final Double VOTE_SCALING = 2.0;

    public MovieData(JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.releaseDate = jsonObject.getString("release_date");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.movieId = jsonObject.getInt("id");
        popularity = (voteAverage > POPULARITY_MIN_VOTE) ? Popularity.RIPE : Popularity.ROTTEN;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
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

    public int getMovieId() {
        return movieId;
    }

    public String getOverview() {
        return overview;
    }

    public Popularity getPopularity() {
        return popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(posterPath);
        out.writeString(backdropPath);
        out.writeString(originalTitle);
        out.writeString(overview);
        out.writeString(releaseDate);
        out.writeDouble(voteAverage);
        out.writeInt(popularity.ordinal());
        out.writeInt(movieId);
    }

    public static final Parcelable.Creator<MovieData> CREATOR
            = new Parcelable.Creator<MovieData>() {
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    private MovieData(Parcel in) {
        posterPath = in.readString();
        backdropPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
        popularity = Popularity.values()[in.readInt()];
        movieId = in.readInt();
    }
}
