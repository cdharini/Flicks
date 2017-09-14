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

import static com.projects.cdharini.flicks.R.id.tvOverview;
import static com.projects.cdharini.flicks.R.id.tvTitle;

/**
 * Created by dharinic on 9/13/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<MovieData> {

    public MovieArrayAdapter(Context context, List<MovieData> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get data
        MovieData movieData = getItem(position);

        ViewHolder viewHolder;
        //inflate view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.title = (TextView) convertView.findViewById(tvTitle);
            viewHolder.overview = (TextView) convertView.findViewById(tvOverview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //populate data via view holder
        viewHolder.image.setImageResource(0);
        viewHolder.title.setText(movieData.getOriginalTitle());
        viewHolder.overview.setText(movieData.getOverview());

        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movieData.getBackdropPath()).into(viewHolder.image);
        } else {
            Picasso.with(getContext()).load(movieData.getPosterPath()).into(viewHolder.image);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView overview;
        ImageView image;
    }
}
