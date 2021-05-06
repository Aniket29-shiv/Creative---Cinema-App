package com.oxoo.spagreen.network.apis;

import com.oxoo.spagreen.network.model.ResponseStatus;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SetPasswordApi {
    @FormUrlEncoded
    @POST("set_password")
    Call<ResponseStatus> setPassword(@Header("API-KEY") String apiKey,
                                     @Field("user_id") String userId,
                                     @Field("password") String password,
                                     @Field("firebase_auth_uid") String firebaseUID);
}
