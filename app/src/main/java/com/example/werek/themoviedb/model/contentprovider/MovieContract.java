package com.example.werek.themoviedb.model.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by werek on 16.02.2017.
 */

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.example.werek.themoviedb.model.contentprovider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVOURITES = "favourites";

    public static final class FavouriteEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVOURITES)
                .build();

        public static final String TABLE_NAME = "favourites";
        public static final String _POSTER_PATH = "posterPath";
        public static final String _BACKDROP_PATH = "backdropPath";
        public static final String _ADULT = "adult";
        public static final String _OVERVIEW = "overview";
        public static final String _RELEASE_DATE = "releaseDate";
        public static final String _ORIGINAL_TITLE = "originalTitle";
        public static final String _ORIGINAL_LANGUAGE = "originalLanguage";
        public static final String _TITLE = "title";
        public static final String _POPULARITY = "popularity";
        public static final String _VOTE_COUNT = "voteCount";
        public static final String _VIDEO = "video";
        public static final String _VOTE_AVERAGE = "voteAverage";

        public static Uri buildEntryUri(int id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        };
    }
}
