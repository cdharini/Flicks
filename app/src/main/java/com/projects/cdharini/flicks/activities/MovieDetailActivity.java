package com.projects.cdharini.flicks.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.projects.cdharini.flicks.R;
import com.projects.cdharini.flicks.models.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Activity to show additional movie details
 */
public class MovieDetailActivity extends YouTubeBaseActivity {
    public static final String MOVIE_DATA_EXTRA = "extraMovieData";

    private YouTubePlayerView pvTrailer;
    private TextView tvMovieTitle;
    private TextView tvSynopsis;
    private RatingBar rbRatings;
    private TextView tvReleaseDate;
    private String videoURL;
    private String videoURLFormat = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US";
    private String youtubeVideoKey = "5xVh-7ywKpE";
    private static final String YOUTUBE_API_KEY = "AIzaSyAsVspYxT1-vUeOo8xdNS89L7HOwsSNmFg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvMovieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        rbRatings = (RatingBar) findViewById(R.id.rbRatings);
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        pvTrailer = (YouTubePlayerView) findViewById(R.id.playerTrailer);

        final MovieData movieData = getIntent().getExtras().getParcelable(MOVIE_DATA_EXTRA);

        videoURL = String.format(videoURLFormat, movieData.getMovieId());

        tvMovieTitle.setText(movieData.getOriginalTitle());
        tvReleaseDate.setText(movieData.getReleaseDate());
        tvSynopsis.setText(movieData.getOverview());
        rbRatings.setRating(Float.valueOf(String.valueOf(
                movieData.getVoteAverage()/MovieData.VOTE_SCALING)));

        pvTrailer.initialize(YOUTUBE_API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        final YouTubePlayer youTubePlayer, boolean b) {
                        // do any work here to cue video, play video, etc.
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get(videoURL, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    youtubeVideoKey = getVideoKey(response);
                                if (youtubeVideoKey != null) {
                                    if (movieData.getPopularity().equals(MovieData.Popularity.ROTTEN)) {
                                        youTubePlayer.cueVideo(youtubeVideoKey);
                                    } else {
                                        youTubePlayer.loadVideo(youtubeVideoKey);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString,
                                                  Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                            }
                        });
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    public static String getVideoKey(JSONObject response) {
        String key = "";
        try {
            JSONArray array = response.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                if (obj.getString("type").equals("Trailer")) {
                    key = obj.getString("key");
                }
            }
        } catch (JSONException e) {
            key = null;
        }
        return key;
    }
}
