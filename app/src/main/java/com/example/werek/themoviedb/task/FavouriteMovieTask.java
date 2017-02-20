package com.example.werek.themoviedb.task;

import android.os.AsyncTask;

import com.example.werek.themoviedb.model.FavouriteManager;
import com.example.werek.themoviedb.model.MoviesList;

/**
 * Created by werek on 20.02.2017.
 */

public class FavouriteMovieTask extends AsyncTask<String, Void, MoviesList> {
    public static final String TAG = AsyncMovieTask.class.getName();
    private final AsyncMovieTask.MovieLoaderListener mListener;

    public FavouriteMovieTask(AsyncMovieTask.MovieLoaderListener listener) {
        mListener = listener;
    }

    @Override
    protected MoviesList doInBackground(String... params) {
        return new FavouriteManager().fetchFavouriteList();
    }

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    @Override
    protected void onPreExecute() {
        mListener.onPreExecute();
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param moviesList The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(MoviesList moviesList) {
        mListener.onMovieListReady(moviesList);
    }

}
