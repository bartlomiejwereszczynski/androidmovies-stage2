package com.example.werek.themoviedb.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.werek.themoviedb.model.MoviesList;
import com.example.werek.themoviedb.task.AsyncMovieTask;
import com.example.werek.themoviedb.util.EndlessRecyclerViewScrollListener;


public class MovieAdapterPaginated extends MovieAdapter implements EndlessRecyclerViewScrollListener.LoadMore, AsyncMovieTask.MovieLoaderListener {
    public static final String TAG = MovieAdapterPaginated.class.getName();

    public MovieAdapterPaginated(@Nullable MovieDetailsListener movieDetails) {
        super(movieDetails);
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        String sorting = mMovieList != null ? mMovieList.getType() : null;
        int nextPage = mMovieList != null ? mMovieList.getPage() + 1 : 1;

        Log.d(TAG, "onLoadMore: page(" + page + "), totalItemCount(" + totalItemsCount + ")");
        Log.d(TAG, "onLoadMore: sorting(" + sorting + "), nextPage(" + nextPage + ")");
        new AsyncMovieTask(this, page).execute(sorting);
    }

    @Override
    public void onPreExecute() {
        // not used
    }

    @Override
    public void onMovieListReady(@Nullable MoviesList moviesList) {
        if (moviesList != null) {
            appendMoviesList(moviesList);
        }
    }
}
