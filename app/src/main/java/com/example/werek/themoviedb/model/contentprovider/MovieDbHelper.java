package com.example.werek.themoviedb.model.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.werek.themoviedb.model.contentprovider.MovieContract.FavouriteEntry;

/**
 * Created by werek on 16.02.2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + FavouriteEntry.TABLE_NAME + " ("
                + FavouriteEntry._ID + " INTEGER PRIMARY KEY,"
                + FavouriteEntry._TITLE + " TEXT,"
                + FavouriteEntry._POSTER_PATH + " TEXT,"
                + FavouriteEntry._ADULT + " INTEGER,"
                + FavouriteEntry._OVERVIEW + " TEXT,"
                + FavouriteEntry._RELEASE_DATE + " TEXT,"
                + FavouriteEntry._ORIGINAL_TITLE + " TEXT,"
                + FavouriteEntry._ORIGINAL_LANGUAGE + " TEXT,"
                + FavouriteEntry._BACKDROP_PATH + " TEXT,"
                + FavouriteEntry._POPULARITY + " REAL,"
                + FavouriteEntry._VOTE_COUNT + " INTEGER,"
                + FavouriteEntry._VOTE_AVERAGE + " REAL,"
                + FavouriteEntry._VIDEO + " INTEGER)";
        db.execSQL(create);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
