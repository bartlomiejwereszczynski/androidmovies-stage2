package com.example.werek.themoviedb.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.werek.themoviedb.model.Movie;
import com.example.werek.themoviedb.model.MoviesList;
import com.example.werek.themoviedb.model.contentprovider.MovieContract;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    /**
     * constructor initializes retrofit library
     */
    public MovieDbApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MOVIEDB_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * initializes API service endpoints interface
     *
     * @return initialized API service
     */
    private MovieDbService movieDb() {
        if (service == null) {
            service = retrofit.create(MovieDbService.class);
        }
        return service;
    }

    /**
     * language used for return data
     *
     * @return locale
     */
    public String getLanguage() {
        return language;
    }

    /**
     * language used for return data
     *
     * @param language locale
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return The Movie Database access key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * The Movie Database access key
     *
     * @param apiKey
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * returns first page of popular movies information
     *
     * runs on Main thread synchronously
     *
     * @return list of movies
     */
    public MoviesList popular() {
        return popular(1);
    }

    /**
     * returns specified page of popular movies information
     *
     * runs on Main thread synchronously
     *
     * @param page page number to fetch
     * @return list of movies
     */
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
        return response.body().setType(Preferences.POPULAR);
    }

    /**
     * returns first page of top rated movies information
     *
     * runs on Main thread synchronously
     *
     * @return list of movies
     */
    public MoviesList topRated() {
        return topRated(1);
    }

    /**
     * returns specified page of top rated movies information
     *
     * runs on Main thread synchronously
     *
     * @param page page number to fetch
     * @return list of movies
     */
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
        return response.body().setType(Preferences.TOP_RATED);
    }

    /**
     * builds url for the movie db image based on size and path
     *
     * @param size size identifier
     * @param path relative path to image
     * @return composed full url to image with given size
     */
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

    /**
     * transforms Movie object to ContentValues to use in ContentProvider insert/update methods
     *
     * @param movie movie object which needs to be transformed for insert/update operation
     * @return movie values in form of ContentValues
     */
    public static ContentValues toContentValue(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.FavouriteEntry._ID, movie.getId());
        cv.put(MovieContract.FavouriteEntry._TITLE, movie.getTitle());
        cv.put(MovieContract.FavouriteEntry._POSTER_PATH, movie.getPosterPath());
        cv.put(MovieContract.FavouriteEntry._ADULT, movie.getAdult() ? 1 : 0);
        cv.put(MovieContract.FavouriteEntry._OVERVIEW, movie.getOverview());
        cv.put(MovieContract.FavouriteEntry._RELEASE_DATE, movie.getReleaseDate());
        cv.put(MovieContract.FavouriteEntry._ORIGINAL_TITLE, movie.getOriginalTitle());
        cv.put(MovieContract.FavouriteEntry._ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        cv.put(MovieContract.FavouriteEntry._BACKDROP_PATH, movie.getBackdropPath());
        cv.put(MovieContract.FavouriteEntry._POPULARITY, movie.getPopularity());
        cv.put(MovieContract.FavouriteEntry._VOTE_COUNT, movie.getVoteCount());
        cv.put(MovieContract.FavouriteEntry._VOTE_AVERAGE, movie.getVoteAverage());
        cv.put(MovieContract.FavouriteEntry._VIDEO, movie.getVideo() ? 1 : 0);
        return cv;
    }

    /**
     * reads current cursor row and creates Movie object from it
     *
     * @param cursor cursor with Movie data
     * @return Movie object with data populated from cursor
     */
    public static Movie fromSingleCursor(Cursor cursor) {
        Movie movie = new Movie();
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._ID) != -1) {
            movie.setId(cursor.getInt(cursor.getColumnIndex(MovieContract.FavouriteEntry._ID)));
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._TITLE) != -1) {
            movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.FavouriteEntry._TITLE)));
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._POSTER_PATH) != -1) {
            movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieContract.FavouriteEntry._POSTER_PATH)));
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._ADULT) != -1) {
            movie.setAdult(cursor.getInt(cursor.getColumnIndex(MovieContract.FavouriteEntry._ADULT)) == 1);
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._OVERVIEW) != -1) {
            movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.FavouriteEntry._OVERVIEW)));
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._RELEASE_DATE) != -1) {
            movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieContract.FavouriteEntry._RELEASE_DATE)));
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._ORIGINAL_TITLE) != -1) {
            movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(MovieContract.FavouriteEntry._ORIGINAL_TITLE)));
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._ORIGINAL_LANGUAGE) != -1) {
            movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(MovieContract.FavouriteEntry._ORIGINAL_LANGUAGE)));
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._BACKDROP_PATH) != -1) {
            movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(MovieContract.FavouriteEntry._BACKDROP_PATH)));
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._POPULARITY) != -1) {
            movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(MovieContract.FavouriteEntry._POPULARITY)));
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._VOTE_COUNT) != -1) {
            movie.setVoteCount(cursor.getInt(cursor.getColumnIndex(MovieContract.FavouriteEntry._VOTE_COUNT)));
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._VOTE_AVERAGE) != -1) {
            movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(MovieContract.FavouriteEntry._VOTE_AVERAGE)));
        }
        if (cursor.getColumnIndex(MovieContract.FavouriteEntry._VIDEO) != -1) {
            movie.setVideo(cursor.getInt(cursor.getColumnIndex(MovieContract.FavouriteEntry._VIDEO)) == 1);
        }
        return movie;
    }
}
