package com.oxoo.spagreen.network.apis;

import com.oxoo.spagreen.network.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UserDataApi {
    @GET("user_details_by_user_id")
    Call<User> getUserData(@Header("API-KEY") String apiKey,
                           @Query("id") String userId);
}
