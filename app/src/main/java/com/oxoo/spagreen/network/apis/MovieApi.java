package com.oxoo.spagreen.network.apis;

import com.oxoo.spagreen.models.home_content.PopularStars;
import com.oxoo.spagreen.models.home_content.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("movies")
    Call<List<Video>> getMovies(@Header("API-KEY") String apiKey,
                                @Query("page") int pahe);

    @GET("content_by_genre_id")
    Call<List<Video>> getMovieByGenreId(@Header("API-KEY") String apiKey,
                                        @Query("id") String id,
                                        @Query("page") int page);
    @GET("content_by_country_id")
    Call<List<Video>> getMovieByCountryId(@Header("API-KEY") String apiKey,
                                        @Query("id") String id,
                                        @Query("page") int page);
    @GET("content_by_star_id")
    Call<List<Video>> getMovieByStarId(@Header("API-KEY") String apiKey,
                                              @Query("id") String id,
                                              @Query("page") int page);

}
