package com.oxoo.spagreen.network.apis;

import com.oxoo.spagreen.models.home_content.HomeContent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface HomeContentApi {

    @GET("home_content_for_android")
    Call<HomeContent> getHomeContent(@Header("API-KEY") String apiKey);
}
