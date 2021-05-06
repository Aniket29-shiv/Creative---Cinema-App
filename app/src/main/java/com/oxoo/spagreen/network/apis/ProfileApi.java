package com.oxoo.spagreen.network.apis;

import android.net.Uri;

import com.oxoo.spagreen.network.model.ResponseStatus;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ProfileApi {
    //@FormUrlEncoded
    @Multipart
    @POST("update_profile")
    Call<ResponseStatus> updateProfile(@Header("API-KEY") String apiKey,
                                       @Part("id") RequestBody id,
                                       @Part("name") RequestBody name,
                                       @Part("email") RequestBody email,
                                       @Part("phone") RequestBody phone,
                                       @Part("password") RequestBody password,
                                       @Part("current_password") RequestBody currentPassword,
                                       @Part MultipartBody.Part photo,
                                       @Part("gender") RequestBody gender);

}
