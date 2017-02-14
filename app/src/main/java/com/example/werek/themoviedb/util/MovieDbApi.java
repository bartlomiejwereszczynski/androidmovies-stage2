package com.example.werek.themoviedb.util;

import android.net.Uri;
import android.util.Log;

import com.example.werek.themoviedb.model.Movie;
import com.example.werek.themoviedb.model.MovieDetails;
import com.example.werek.themoviedb.model.MoviesList;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by werek on 21.01.2017.
 */

public class MovieDbApi {
    private static final String TAG = MovieDbApi.class.getName();
    private static final String MOVIEDB_API = "https://api.themoviedb.org";
    public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p";
    public static final String POSTER_WIDTH_92 = "w92";
    public static final String POSTER_WIDTH_154 = "w154";
    public static final String POSTER_WIDTH_185 = "w185";
    public static final String POSTER_WIDTH_342 = "w342";
    public static final String POSTER_WIDTH_500 = "w500";
    public static final String POSTER_WIDTH_780 = "w780";
    public static final String POSTER_WIDTH_ORIGINAL = "original";
    private Retrofit retrofit;
    private MovieDbService service;

    protected String language;
    private String apiKey;

    public MovieDbApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MOVIEDB_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private MovieDbService movieDb() {
        if (service == null) {
            service = retrofit.create(MovieDbService.class);
        }
        return service;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public MoviesList popular() {
        return popular(1);
    }

    public MoviesList popular(int page) {
        Response<MoviesList> response = null;

        try {
            response = movieDb().popularMovies(apiKey, page, language).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            Log.d(TAG, "popular movies response is null");
            return null;
        }
        if (!response.isSuccessful()) {
            Log.d(TAG, "popular movies response is unsuccessful, got error code: " + response.code());
            try {
                Log.d(TAG, "popular movies response is unsuccessful, got error body: " + response.errorBody().string());
            } catch (IOException e) {
                Log.d(TAG, "popular movies response is unsuccessful, decoding body thrown IOException");
                e.printStackTrace();
            }
            return null;
        }
        return response.body();
    }

    public MoviesList topRated() {
        return topRated(1);
    }

    public MoviesList topRated(int page) {
        Response<MoviesList> response = null;
        try {
            response = movieDb().topRatedMovies(apiKey, page, language).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            Log.d(TAG, "top rated movies response is null");
            return null;
        }
        if (!response.isSuccessful()) {
            Log.d(TAG, "top rated movies response is unsuccessful, got error code: " + response.code());
            try {
                Log.d(TAG, "top rated movies response is unsuccessful, got error body: " + response.errorBody().string());
            } catch (IOException e) {
                Log.d(TAG, "top rated movies response is unsuccessful, decoding body thrown IOException");
                e.printStackTrace();
            }
            return null;
        }
        return response.body();
    }

    public MovieDetails details(int movieId) {
        return details(Integer.valueOf(movieId));
    }

    public MovieDetails details(Movie movie) {
        return details(movie.getId());
    }

    public MovieDetails details(Integer movieId) {
        Response<MovieDetails> response = null;
        try {
            response = movieDb().movieDetails(apiKey, movieId, language).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            Log.d(TAG, "movie details response is null");
            return null;
        }
        if (!response.isSuccessful()) {
            Log.d(TAG, "movie details response is unsuccessful, got error code: " + response.code());
            try {
                Log.d(TAG, "movie details response is unsuccessful, got error body: " + response.errorBody().string());
            } catch (IOException e) {
                Log.d(TAG, "movie details response is unsuccessful, decoding body thrown IOException");
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    public static URL buildImageURL(String size, String path) {
        path = path.startsWith("/") ? path.substring(1) : path;
        String urlString = Uri.parse(MovieDbApi.POSTER_BASE_URL)
                .buildUpon()
                .appendPath(size)
                .appendPath(path)
                .build()
                .toString();
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
