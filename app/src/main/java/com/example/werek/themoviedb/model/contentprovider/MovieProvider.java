package com.example.werek.themoviedb.model.contentprovider;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.werek.themoviedb.model.MovieImageStore;
import com.example.werek.themoviedb.model.contentprovider.MovieContract.FavouriteEntry;
import com.example.werek.themoviedb.util.MovieDbApi;

import java.io.File;
import java.io.IOException;

/**
 * Created by werek on 16.02.2017.
 */

public class MovieProvider extends ContentProvider {
    public static final String TAG = MovieProvider.class.getSimpleName();
    public static final int CODE_FAVOURITES = 100;
    public static final int CODE_FAVOURITES_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenHelper;

    /**
     * @return A UriMatcher that correctly matches the constants for CODE_FAVOURITES and CODE_FAVOURITES_WITH_ID
     */
    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MovieContract.PATH_FAVOURITES, CODE_FAVOURITES);
        matcher.addURI(authority, MovieContract.PATH_FAVOURITES + "/#", CODE_FAVOURITES_WITH_ID);

        return matcher;
    }

    /**
     * In onCreate, we initialize our content provider on startup. This method is called for all
     * registered content providers on the application main thread at application launch time.
     * It must not perform lengthy operations, or application startup will be delayed.
     * <p>
     * Nontrivial initialization (such as opening, upgrading, and scanning
     * databases) should be deferred until the content provider is used (via {@link #query},
     * {@link #bulkInsert(Uri, ContentValues[])}, etc).
     * <p>
     * Deferred initialization keeps application startup fast, avoids unnecessary work if the
     * provider turns out not to be needed, and stops database errors (such as a full disk) from
     * halting application launch.
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }


    /**
     * Handles query requests from clients. We will use this method in Sunshine to query for all
     * of our weather data as well as to query for the weather on a particular day.
     *
     * @param uri           The URI to query
     * @param projection    The list of columns to put into the cursor. If null, all columns are
     *                      included.
     * @param selection     A selection criteria to apply when filtering rows. If null, then all
     *                      rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *                      the values from selectionArgs, in order that they appear in the
     *                      selection.
     * @param sortOrder     How the rows in the cursor should be sorted.
     * @return A Cursor containing the results of the query. In our implementation,
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {

            case CODE_FAVOURITES_WITH_ID: {

                String movieId = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{movieId};

                cursor = mOpenHelper.getReadableDatabase().query(
                        FavouriteEntry.TABLE_NAME,
                        projection,
                        FavouriteEntry._ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);

                break;
            }

            case CODE_FAVOURITES: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        FavouriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Implement this to handle requests for the MIME type of the data at the
     * given URI.  The returned MIME type should start with
     * <code>vnd.android.cursor.item</code> for a single record,
     * or <code>vnd.android.cursor.dir/</code> for multiple items.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     * <p>
     * <p>Note that there are no permissions needed for an application to
     * access this information; if your content provider requires read and/or
     * write permissions, or is not exported, all applications can still call
     * this method regardless of their access permissions.  This allows them
     * to retrieve the MIME type for a URI when dispatching intents.
     *
     * @param uri the URI to query.
     * @return a MIME type string, or {@code null} if there is no type.
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * Implement this to handle requests to insert a new row.
     * As a courtesy, call  notifyChange()}
     * after inserting.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * @param uri    The content:// URI of the insertion request. This must not be {@code null}.
     * @param values A set of column_name/value pairs to add to the database.
     *               This must not be {@code null}.
     * @return The URI for the newly inserted item.
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri inserted;

        switch (match) {
            case CODE_FAVOURITES:
                if (!values.containsKey(FavouriteEntry._ID)) {
                    throw new UnsupportedOperationException("Need movie id to properly insert record into db");
                }
                long id = db.insert(FavouriteEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    inserted = ContentUris.withAppendedId(FavouriteEntry.CONTENT_URI, id);
                    storeImages(
                            values.getAsInteger(FavouriteEntry._ID),
                            values.getAsString(FavouriteEntry._POSTER_PATH),
                            values.getAsString(FavouriteEntry._BACKDROP_PATH)
                    );
                } else {
                    throw new SQLiteException("error while inserting data");
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (inserted != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return inserted;
    }

    /**
     * Deletes data at a given URI with optional arguments for more fine tuned deletions.
     *
     * @param uri           The full URI to query
     * @param selection     An optional restriction to apply to rows when deleting.
     * @param selectionArgs Used in conjunction with the selection statement
     * @return The number of rows deleted
     */
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        int numRowsDeleted;

        /*
         * If we pass null as the selection to SQLiteDatabase#delete, our entire table will be
         * deleted. However, if we do pass null and delete all of the rows in the table, we won't
         * know how many rows were deleted. According to the documentation for SQLiteDatabase,
         * passing "1" for the selection will delete all rows and return the number of rows
         * deleted, which is what the caller of this method expects.
         */
        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {

            case CODE_FAVOURITES:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        FavouriteEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case CODE_FAVOURITES_WITH_ID:
                String movieId = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{movieId};

                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        FavouriteEntry.TABLE_NAME,
                        FavouriteEntry._ID + " = ? ",
                        selectionArguments);
                new MovieImageStore(Integer.valueOf(movieId)).deleteMovieDir();
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /* If we actually deleted any rows, notify that a change has occurred to this URI */
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int updated;

        switch (match) {
            case CODE_FAVOURITES_WITH_ID:
                String movieId = uri.getLastPathSegment();
                String[] whereArgs = new String[]{movieId};
                if (values.containsKey(FavouriteEntry._ID)) {
                    // no need to pass it to db
                    values.remove(FavouriteEntry._ID);
                }
                updated = db.update(
                        FavouriteEntry.TABLE_NAME,
                        values,
                        FavouriteEntry._ID + " = ? ",
                        whereArgs
                );
                storeImages(
                        Integer.valueOf(movieId),
                        values.getAsString(FavouriteEntry._POSTER_PATH),
                        values.getAsString(FavouriteEntry._BACKDROP_PATH)
                );
                break;
            case CODE_FAVOURITES:
                updated = db.update(
                        FavouriteEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updated;
    }

    private void storeImages(int id, String poster, String backdrop) {
        MovieImageStore imageStore = new MovieImageStore(id);
        File posterFile = imageStore.getPoster();
        File backdropFile = imageStore.getBackdrop();
        MovieDbApi movieApi = new MovieDbApi();

        try {
            if (!posterFile.exists()) {
                posterFile.createNewFile();
            }
            if (!backdropFile.exists()) {
                backdropFile.createNewFile();
            }
            movieApi.downloadImageTo(MovieDbApi.POSTER_WIDTH_342, poster, posterFile);
            movieApi.downloadImageTo(MovieDbApi.POSTER_WIDTH_780, backdrop, backdropFile);
            Log.d(TAG, "storeImages: stored poster and backdrop images");
            Log.d(TAG, "storeImages: poster size "+posterFile.length());
            Log.d(TAG, "storeImages: backdrop size "+backdropFile.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * You do not need to call this method. This is a method specifically to assist the testing
     * framework in running smoothly. You can read more at:
     * http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
     */
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
