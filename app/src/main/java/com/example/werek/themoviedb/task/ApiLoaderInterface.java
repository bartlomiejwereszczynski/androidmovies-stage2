package com.example.werek.themoviedb.task;

import android.support.annotation.Nullable;

public interface ApiLoaderInterface<T> {
    /**
     * executed right before api call
     */
    void onPreExecute();

    /**
     * returns parsed response from api
     *
     * @param response parsed response
     */
    void onResponse(@Nullable T response);
}
