package com.example.werek.themoviedb.task;


import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.werek.themoviedb.BuildConfig;
import com.example.werek.themoviedb.model.MoviesList;
import com.example.werek.themoviedb.util.MovieDbApi;
import com.example.werek.themoviedb.util.Preferences;

import java.util.Locale;

public class AsyncMovieTask extends AsyncTask<String, Void, MoviesList> {
    public static final String TAG = AsyncMovieTask.class.getName();
    private final MovieLoaderListener mListener;
    private int mPage = 1;

    public interface MovieLoaderListener {
        void onPreExecute();

        void onMovieListReady(@Nullable MoviesList moviesList);
    }

    public AsyncMovieTask(MovieLoaderListener listener) {
        mListener = listener;
    }

    public AsyncMovieTask(MovieLoaderListener listener, int page) {
        mListener = listener;
        mPage = page;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected MoviesList doInBackground(String... params) {
        String sorting = params[0];
        MovieDbApi api = new MovieDbApi();
        api.setApiKey(BuildConfig.MOVIE_DB_API_KEY);
        String language = Locale.getDefault().toString();
        Log.d(TAG, "language used for TMDB: " + language);
        api.setLanguage(language);
        MoviesList movies;
        switch (sorting) {
            case Preferences.TOP_RATED:
                movies = api.topRated(mPage);
                break;
            case Preferences.POPULAR:
            default:
                movies = api.popular(mPage);
                break;
        }
        return movies;
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
