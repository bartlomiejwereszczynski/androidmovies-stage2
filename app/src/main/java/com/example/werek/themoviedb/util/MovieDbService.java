package com.example.werek.themoviedb.util;

import com.example.werek.themoviedb.model.MoviesList;
import com.example.werek.themoviedb.model.ReviewList;
import com.example.werek.themoviedb.model.VideosList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by werek on 21.01.2017.
 */

public interface MovieDbService {
    @GET("/3/movie/popular")
    Call<MoviesList> popularMovies(@Query("api_key") String apiKey, @Query("page") int page, @Query("language") String language);

    @GET("/3/movie/top_rated")
    Call<MoviesList> topRatedMovies(@Query("api_key") String apiKey, @Query("page") int page, @Query("language") String language);

    @GET("/t/p/{size}/{image}")
    Call<ResponseBody> downloadImage(@Path("size") String size, @Path("image") String image);

    @GET("/movie/{movie_id}/videos")
    Call<VideosList> movieVideos(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("/movie/{movie_id}/reviews")
    Call<ReviewList> movieReviews(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("language") String language);
}
