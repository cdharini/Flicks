package com.projects.cdharini.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.cdharini.flicks.R;
import com.projects.cdharini.flicks.models.MovieData;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.projects.cdharini.flicks.R.id.tvOverview;
import static com.projects.cdharini.flicks.R.id.tvTitle;

/**
 * Movie adapter for ListView in MainActivity
 * Created by dharinic on 9/13/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<MovieData> {

    public MovieArrayAdapter(Context context, List<MovieData> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getPopularity().ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return MovieData.Popularity.values().length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get data
        MovieData movieData = getItem(position);

        int orientation = getContext().getResources().getConfiguration().orientation;
        RipeViewHolder ripeViewHolder = new RipeViewHolder();
        RottenViewHolder rottenViewHolder = new RottenViewHolder();

        int type = getItemViewType(position);
        //inflate view
        if (convertView == null) {

            convertView = getInflatedViewForType(type);
            switch (type) {
                // ripe
                case 0:
                    ripeViewHolder.image = (ImageView) convertView.findViewById(R.id.ivMovieImage);
                    convertView.setTag(ripeViewHolder);
                    break;
                default:
                    // rotten movies are default views
                    rottenViewHolder.image = (ImageView) convertView.findViewById(R.id.ivMovieImage);
                    rottenViewHolder.title = (TextView) convertView.findViewById(tvTitle);
                    rottenViewHolder.overview = (TextView) convertView.findViewById(tvOverview);
                    convertView.setTag(rottenViewHolder);
                    break;
            }
        } else {
            switch (type) {
                case 0:
                    ripeViewHolder = (RipeViewHolder) convertView.getTag();
                    break;
                default:
                    rottenViewHolder = (RottenViewHolder) convertView.getTag();
                    break;
            }

        }

        switch(type) {
            case 0:
                ripeViewHolder.image.setImageResource(0);
                Picasso.with(getContext()).load(movieData.getFullBackdropPath()).fit()
                        .centerInside().placeholder(R.mipmap.placeholder_img)
                        .transform(new RoundedCornersTransformation(10, 10))
                        .error(R.mipmap.placeholder_error_img).into(ripeViewHolder.image);
                break;
            default:
                //populate data via view holder
                rottenViewHolder.image.setImageResource(0);
                rottenViewHolder.title.setText(movieData.getOriginalTitle());
                rottenViewHolder.overview.setText(movieData.getOverview());

                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Picasso.with(getContext()).load(movieData.getBackdropPath())
                            .fit().centerInside()
                            .transform(new RoundedCornersTransformation(10, 10))
                            .placeholder(R.mipmap.placeholder_img)
                            .error(R.mipmap.placeholder_error_img).into(rottenViewHolder.image);
                } else {
                    Picasso.with(getContext()).load(movieData.getPosterPath())
                            .fit().centerInside()
                            .placeholder(R.mipmap.placeholder_img)
                            .transform(new RoundedCornersTransformation(10, 10))
                            .error(R.mipmap.placeholder_error_img).into(rottenViewHolder.image);
                }
                break;
        }


        return convertView;
    }

    private View getInflatedViewForType(int type){
        if (type == MovieData.Popularity.RIPE.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_ripe, null);
        } else {
            return  LayoutInflater.from(getContext()).inflate(R.layout.item_movie_rotten, null);
        }
    }

    static class RottenViewHolder {
        public TextView title;
        public TextView overview;
        public ImageView image;
    }

    static class RipeViewHolder {
        public ImageView image;
    }
}
