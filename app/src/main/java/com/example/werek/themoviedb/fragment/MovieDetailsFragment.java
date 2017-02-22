package com.example.werek.themoviedb.fragment;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.activity.MainActivity;
import com.example.werek.themoviedb.databinding.FragmentMovieDetailsBinding;
import com.example.werek.themoviedb.model.FavouriteManager;
import com.example.werek.themoviedb.model.Movie;
import com.example.werek.themoviedb.model.contentprovider.MovieContract;
import com.example.werek.themoviedb.viewmodel.MovieDetailsViewModel;

public class MovieDetailsFragment extends Fragment implements MovieLoaderInterface {
    public static final String TAG = MovieDetailsFragment.class.getSimpleName();
    FragmentMovieDetailsBinding mBinding;
    private Movie mMovie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        if (getArguments() != null && getArguments().containsKey(MainActivity.MOVIE_EXTRA)) {
            Log.d(TAG, "onCreateView: loading movie from arguments");
            mMovie = getArguments().getParcelable(MainActivity.MOVIE_EXTRA);
        } else if (savedInstanceState != null && savedInstanceState.containsKey(MainActivity.MOVIE_EXTRA)) {
            Log.d(TAG, "onCreateView: loading movie from saved state");
            mMovie = savedInstanceState.getParcelable(MainActivity.MOVIE_EXTRA);
        }
        setupFavouriteListener();
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMovie != null) {
            onLoadMovie(mMovie);
        }
    }

    public void onLoadMovie(Movie movie) {
        Log.i(TAG, "onLoadMovie: loading movie " + movie);
        mMovie = movie;
        if (mBinding != null) {
            mBinding.setMovie(new MovieDetailsViewModel(mMovie));
            switchFavouriteState(mMovie.isFavourite);
            mBinding.getRoot().requestLayout();
        }
    }

    private void switchFavouriteState(String favState) {
        switch (favState) {
            case Movie.FAV_YES:
                mBinding.ivFavourite.setImageDrawable(getResources().getDrawable(android.R.drawable.star_big_on));
                break;
            case Movie.FAV_UNKNOWN:
                new AsyncTask<Movie, Void, String>() {
                    @Override
                    protected String doInBackground(Movie... params) {
                        Movie movie = params[0];
                        Uri movieUri = MovieContract.FavouriteEntry.buildEntryUri(movie.getId());
                        Cursor cursor = MovieDetailsFragment.this.getContext().getContentResolver().query(movieUri, null, null, null, null);
                        String result = cursor.getCount() > 0 ? Movie.FAV_YES : Movie.FAV_NO;
                        cursor.close();
                        return result;
                    }

                    @Override
                    protected void onPostExecute(String isFavourite) {
                        mMovie.isFavourite = isFavourite;
                        if (!isFavourite.equals(Movie.FAV_UNKNOWN)) {
                            Log.d(TAG, "onPostExecute: movie favourite status is " + isFavourite);
                            switchFavouriteState(isFavourite);
                        }
                    }
                }.execute(mMovie);
            case Movie.FAV_NO:
            default:
                mBinding.ivFavourite.setImageDrawable(getResources().getDrawable(android.R.drawable.star_big_off));
                break;
        }
    }

    public void setupFavouriteListener() {
        mBinding.ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavouriteManager fm = new FavouriteManager(getContext());
                if (mMovie.isFavourite.equals(Movie.FAV_YES)) {
                    mMovie.isFavourite = Movie.FAV_NO;
                    fm.removeFavourite(mMovie);
                } else {
                    mMovie.isFavourite = Movie.FAV_YES;
                    fm.storeFavourite(mMovie);
                }
                switchFavouriteState(mMovie.isFavourite);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MainActivity.MOVIE_EXTRA, mMovie);
    }
}
