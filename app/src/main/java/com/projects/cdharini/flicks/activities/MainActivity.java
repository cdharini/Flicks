package com.projects.cdharini.flicks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.projects.cdharini.flicks.R;
import com.projects.cdharini.flicks.adapters.MovieArrayAdapter;
import com.projects.cdharini.flicks.models.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Main activity that displays a list of now playing movies
 * Launches MovieDetailActivity when user clicks list item
 */
public class MainActivity extends AppCompatActivity {

    private MovieArrayAdapter movieAdapter;
    private ListView lvMovies;
    private ArrayList<MovieData> moviesData;

    // URL to fetch now playing movies
    private static final String MOVIE_DATA_URL =
            "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMovies = (ListView) findViewById(R.id.lvMovies);
        moviesData = new ArrayList<MovieData>();
        movieAdapter = new MovieArrayAdapter(this, moviesData);
        lvMovies.setAdapter(movieAdapter);

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id){
                MovieData movie = movieAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.MOVIE_DATA_EXTRA, movie);
                startActivity(intent);
            }
        });

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MOVIE_DATA_URL, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray movieJsonResults = response.getJSONArray("results");
                    moviesData.addAll(MovieData.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
