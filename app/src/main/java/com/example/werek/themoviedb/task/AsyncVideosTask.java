package com.example.werek.themoviedb.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.werek.themoviedb.BuildConfig;
import com.example.werek.themoviedb.model.VideosList;
import com.example.werek.themoviedb.util.MovieDbApi;

import java.util.Locale;

public class AsyncVideosTask extends AsyncTask<Integer, Void, VideosList> {
    public static final String TAG = AsyncVideosTask.class.getSimpleName();
    ApiLoaderInterface<VideosList> mLoader;

    public AsyncVideosTask(ApiLoaderInterface<VideosList> mLoader) {
        this.mLoader = mLoader;
    }

    @Override
    protected void onPreExecute() {
        mLoader.onPreExecute();
    }

    @Override
    protected VideosList doInBackground(Integer... params) {
        Integer movieId = params[0];
        MovieDbApi api = new MovieDbApi();
        api.setApiKey(BuildConfig.MOVIE_DB_API_KEY);
        String language = Locale.getDefault().toString();
        Log.d(TAG, "language used for TMDB: " + language);
        api.setLanguage(language);
        return api.movieVideos(movieId);
    }

    @Override
    protected void onPostExecute(VideosList videosList) {
        mLoader.onResponse(videosList);
    }
}
