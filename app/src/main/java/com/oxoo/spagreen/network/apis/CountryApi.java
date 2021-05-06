package com.oxoo.spagreen.network.apis;

import com.oxoo.spagreen.models.home_content.AllCountry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CountryApi {

    @GET("all_country")
    Call<List<AllCountry>> getAllCountry(@Header("API-KEY") String apiKey);

}
