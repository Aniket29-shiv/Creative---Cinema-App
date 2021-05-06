package com.oxoo.spagreen.network.apis;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MovieRequestApi {
    @FormUrlEncoded
    @POST("request")
    Call<ResponseBody> submitRequest(@Header("API-KEY") String apiKey,
                                    @Field("name") String name,
                                    @Field("email") String email,
                                    @Field("movie_name") String movieName,
                                    @Field("message") String message
    );
}
