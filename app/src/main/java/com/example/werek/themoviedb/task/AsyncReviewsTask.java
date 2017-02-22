package com.example.werek.themoviedb.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.werek.themoviedb.BuildConfig;
import com.example.werek.themoviedb.model.ReviewList;
import com.example.werek.themoviedb.util.MovieDbApi;

import java.util.Locale;

public class AsyncReviewsTask extends AsyncTask<Integer, Void, ReviewList> {
    public static final String TAG = AsyncReviewsTask.class.getSimpleName();
    ApiLoaderInterface<ReviewList> mLoader;

    public AsyncReviewsTask(ApiLoaderInterface<ReviewList> mLoader) {
        this.mLoader = mLoader;
    }

    @Override
    protected void onPreExecute() {
        mLoader.onPreExecute();
    }

    @Override
    protected ReviewList doInBackground(Integer... params) {
        Integer movieId = params[0];
        MovieDbApi api = new MovieDbApi();
        api.setApiKey(BuildConfig.MOVIE_DB_API_KEY);
        String language = Locale.getDefault().toString();
        Log.d(TAG, "language used for TMDB: " + language);
        api.setLanguage(language);
        return api.movieReviews(movieId);
    }

    @Override
    protected void onPostExecute(ReviewList reviewList) {
        mLoader.onResponse(reviewList);
    }
}
