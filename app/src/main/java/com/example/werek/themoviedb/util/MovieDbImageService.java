package com.example.werek.themoviedb.util;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by werek on 21.01.2017.
 */

public interface MovieDbImageService {
    @GET("/t/p/{size}/{image}")
    Call<ResponseBody> downloadImage(@Path("size") String size, @Path("image") String image);
}
