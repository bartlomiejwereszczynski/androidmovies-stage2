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
        String type = null;
        int nextPage;
        if (mMovieList == null) {
            nextPage = 1;
        } else {
            if (mMovieList.getPage().equals(mMovieList.getTotalPages())) return;
            nextPage = mMovieList.getPage() + 1;
            type = mMovieList.getType();
        }
        Log.d(TAG, "onLoadMore: page(" + page + "), totalItemCount(" + totalItemsCount + ")");
        Log.d(TAG, "onLoadMore: type(" + type + "), nextPage(" + nextPage + ")");
        new AsyncMovieTask(this, page).execute(type);
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
