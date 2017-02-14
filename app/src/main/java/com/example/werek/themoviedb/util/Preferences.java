package com.example.werek.themoviedb.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by werek on 24.01.2017.
 */

public class Preferences {
    private static final String SORTING = "listSorting";
    public static final String TOP_RATED = "topRated";
    public static final String POPULAR = "popular";

    public static void setSorting(Context context, String choice) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().putString(SORTING, choice).apply();
    }

    public static String getSorting(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(SORTING, POPULAR);
    }
}
