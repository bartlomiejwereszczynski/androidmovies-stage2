package com.example.werek.themoviedb.model;

import android.content.Context;
import android.util.Log;

import com.example.werek.themoviedb.AppApplication;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by werek on 19.02.2017.
 */

public class MovieImageStore {
    public static final String TAG = MovieImageStore.class.getSimpleName();
    public static final String POSTER = "poster";
    public static final String BACKDROP = "backdrop";
    private int mId;
    private Context mContext;

    public MovieImageStore(int id) {
        mId = id;
        mContext = AppApplication.app;
    }

    public MovieImageStore(int id, Context context) {
        mId = id;
        mContext = context;
    }

    public File movieDir() {
        String path = mContext.getFilesDir().getAbsolutePath() + "/movie/" + mId;
        return new File(path);
    }

    public void deleteMovieDir() {
        File movieDir = movieDir();
        try {
            FileUtils.deleteDirectory(movieDir);
            Log.d(TAG, "deleteMovieDir: deleted movie directory "+movieDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getMovieFilePath(String name) {
        File movieDir = movieDir();
        if (!movieDir.exists()) {
            movieDir.mkdirs();
        }
        return new File(movieDir, name);
    }

    public File getPoster() {
        return getMovieFilePath(POSTER);
    }

    public File getBackdrop() {
        return getMovieFilePath(BACKDROP);
    }
}
