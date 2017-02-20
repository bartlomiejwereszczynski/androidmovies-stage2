package com.example.werek.themoviedb.viewmodel;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.model.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;

/**
 * Created by werek on 19.02.2017.
 */

public class MovieDetailsViewModel {
    private static final String TAG = MovieDetailsViewModel.class.getSimpleName();
    private Movie mMovie;

    public MovieDetailsViewModel(Movie movie) {
        mMovie = movie;
    }

    public String getTitle() {
        return mMovie.getTitle();
    }

    public String getOriginalTitle() {
        return mMovie.getOriginalTitle();
    }

    public URL getPosterUrl() {
        return mMovie.getPosterUrl();
    }

    public URL getBackdropUrl() {
        return mMovie.getBackdropUrl();
    }

    public String getReleaseDate() {
        return mMovie.getReleaseDate();
    }

    public String getOverview() {
        return mMovie.getOverview();
    }

    public String getVoteAverage() {
        return String.valueOf(mMovie.getVoteAverage());
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, URL imageUrl) {
        if (imageUrl == null) {
            Log.d(TAG, "loadImage: empty image url");
            return;
        }
        Picasso.with(view.getContext())
                .load(imageUrl.toString())
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_chain_broken)
                .into(view);
    }
}
