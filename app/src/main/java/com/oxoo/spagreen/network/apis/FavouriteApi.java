package com.oxoo.spagreen.network.apis;

import com.oxoo.spagreen.models.Movie;
import com.oxoo.spagreen.network.model.FavoriteModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface FavouriteApi {

    @GET("favorite")
    Call<List<Movie>> getFavoriteList(@Header("API-KEY") String apiKey,
                                      @Query("user_id") String userId,
                                      @Query("page") int page);
    @GET("add_favorite")
    Call<FavoriteModel> addToFavorite(@Header("API-KEY") String apiKey,
                                      @Query("user_id") String userId,
                                      @Query("videos_id") String videoId);

    @GET("remove_favorite")
    Call<FavoriteModel> removeFromFavorite(@Header("API-KEY") String apiKey,
                                           @Query("user_id") String userId,
                                           @Query("videos_id") String videoId);

    @GET("verify_favorite_list")
    Call<FavoriteModel> verifyFavoriteList(@Header("API-KEY") String apiKey,
                                           @Query("user_id") String userId,
                                           @Query("videos_id") String videoId);
}
