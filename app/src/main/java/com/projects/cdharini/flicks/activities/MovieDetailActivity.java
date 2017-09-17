package com.projects.cdharini.flicks.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.projects.cdharini.flicks.R;
import com.projects.cdharini.flicks.models.MovieData;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Activity to show additional movie details
 */
public class MovieDetailActivity extends AppCompatActivity {
    public static final String MOVIE_DATA_EXTRA = "extraMovieData";

    private ImageView ivPoster;
    private TextView tvMovieTitle;
    private TextView tvSynopsis;
    private RatingBar rbRatings;
    private TextView tvReleaseDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ivPoster = (ImageView) findViewById(R.id.ivPoster);
        tvMovieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        rbRatings = (RatingBar) findViewById(R.id.rbRatings);
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);

        MovieData movieData = getIntent().getExtras().getParcelable(MOVIE_DATA_EXTRA);

        Picasso.with(this).load(movieData.getPosterPath())
                .placeholder(R.mipmap.placeholder_img)
                .fit().centerInside()
                .transform(new RoundedCornersTransformation(10, 10))
                .error(R.mipmap.placeholder_error_img).into(ivPoster);

        tvMovieTitle.setText(movieData.getOriginalTitle());
        tvReleaseDate.setText(movieData.getReleaseDate());
        tvSynopsis.setText(movieData.getOverview());
        rbRatings.setRating(Float.valueOf(String.valueOf(movieData.getVoteAverage()/MovieData.VOTE_SCALING)));
    }
}
