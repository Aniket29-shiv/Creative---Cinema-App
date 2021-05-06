package com.oxoo.spagreen.network.apis;

import com.oxoo.spagreen.models.single_details_tv.SingleDetailsTV;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SingleDetailsTVApi {
    @GET("single_details")
    Call<SingleDetailsTV> getSingleDetails(@Header("API-KEY") String key,
                                           @Query("type") String type,
                                           @Query("id") String id);
}
