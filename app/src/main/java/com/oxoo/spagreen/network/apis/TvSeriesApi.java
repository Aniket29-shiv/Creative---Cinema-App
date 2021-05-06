package com.oxoo.spagreen.network.apis;

import com.oxoo.spagreen.models.home_content.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TvSeriesApi {

    @GET("tvseries")
    Call<List<Video>> getTvSeries(@Header("API-KEY") String apiKey,
                                  @Query("page") int page);


}
