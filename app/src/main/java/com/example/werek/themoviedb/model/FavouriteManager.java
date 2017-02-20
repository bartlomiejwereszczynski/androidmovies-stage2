package com.example.werek.themoviedb.model;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.werek.themoviedb.AppApplication;
import com.example.werek.themoviedb.model.contentprovider.MovieContract.FavouriteEntry;
import com.example.werek.themoviedb.util.MovieDbApi;
import com.example.werek.themoviedb.util.Preferences;

import java.util.ArrayList;

/**
 * Created by werek on 19.02.2017.
 */

public class FavouriteManager {
    public static final String TAG = FavouriteManager.class.getSimpleName();
    private final Context mContext;
    public static final int INSERT=10001;
    public static final int UPDATE=10002;

    public FavouriteManager() {
        mContext = AppApplication.app;
    }

    public FavouriteManager(Context context) {
        mContext = context;
    }

    public FavouriteManager storeFavourite(Movie movie) {
        ContentValues cv = MovieDbApi.toContentValue(movie);
        ContentResolver contentResolver = mContext.getContentResolver();
        Movie stored = fetchFavourite(movie.getId());
        if (stored != null) {
            new AsyncQueryHandler(contentResolver){}.startUpdate(UPDATE,null,FavouriteEntry.buildEntryUri(movie.getId()),cv, null,null);
            Log.d(TAG, "storeFavourite: updated movie " + movie);
        } else {
            new AsyncQueryHandler(contentResolver){}.startInsert(INSERT,null,FavouriteEntry.CONTENT_URI, cv);
            Log.d(TAG, "storeFavourite: inserted movie " + movie);
        }
        return this;
    }

    public FavouriteManager removeFavourite(Movie movie) {
        ContentResolver contentResolver = mContext.getContentResolver();
        Movie stored = fetchFavourite(movie.getId());
        if (stored != null) {
            contentResolver.delete(FavouriteEntry.buildEntryUri(movie.getId()), null, null);
            Log.d(TAG, "removeFavourite: deleted movie from db " + movie);
        }
        return this;
    }

    public Movie fetchFavourite(int id) {
        Uri movieUri = FavouriteEntry.buildEntryUri(id);
        Cursor cursor = mContext.getContentResolver().query(movieUri, null, null, null, null);
        if (cursor.moveToFirst()) {
            Movie stored = MovieDbApi.fromSingleCursor(cursor);
            stored.isFavourite = Movie.FAV_YES;
            cursor.close();
            return stored;
        } else {
            cursor.close();
        }
        return null;
    }

    public MoviesList fetchFavouriteList() {
        MoviesList list = new MoviesList();
        list.setPage(1);
        list.setTotalPages(1);
        list.setType(Preferences.FAVOURITE);
        list.setResults(new ArrayList<Movie>());

        Cursor cursor = mContext.getContentResolver().query(FavouriteEntry.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Movie movie = MovieDbApi.fromSingleCursor(cursor);
                movie.isFavourite = Movie.FAV_YES;
                list.getResults().add(movie);
            }
        }

        list.setTotalResults(list.getResults().size());
        return list;
    }
}
