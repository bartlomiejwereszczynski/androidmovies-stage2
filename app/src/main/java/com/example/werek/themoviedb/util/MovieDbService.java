package com.example.werek.themoviedb.util;

import com.example.werek.themoviedb.model.MoviesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by werek on 21.01.2017.
 */

public interface MovieDbService {
    @GET("/3/movie/popular")
    Call<MoviesList> popularMovies(@Query("api_key") String apiKey, @Query("page") int page, @Query("language") String language);

    @GET("/3/movie/top_rated")
    Call<MoviesList> topRatedMovies(@Query("api_key") String apiKey, @Query("page") int page, @Query("language") String language);
}
