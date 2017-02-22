package com.example.werek.themoviedb.fragment;

import com.example.werek.themoviedb.model.Movie;

public interface MovieLoaderInterface {
    /**
     * receives movie to load
     *
     * @param movie movie to load
     */
    void onLoadMovie(Movie movie);
}
