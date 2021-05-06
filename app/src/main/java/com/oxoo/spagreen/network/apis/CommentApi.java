package com.oxoo.spagreen.network.apis;

import com.oxoo.spagreen.models.GetCommentsModel;
import com.oxoo.spagreen.models.PostCommentModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommentApi {

    @GET("all_comments")
    Call<List<GetCommentsModel>> getAllComments(@Header("API-KEY") String apiKey,
                                                @Query("id") String id);

    @FormUrlEncoded
    @POST("comments")
    Call<PostCommentModel> postComment(@Header("API-KEY") String apiKey,
                                       @Field("videos_id") String videoId,
                                       @Field("user_id") String userId,
                                       @Field("comment") String comment);

    @FormUrlEncoded
    @POST("add_replay")
    Call<PostCommentModel> postReply(@Header("API-KEY") String apiKey,
                                     @Field("videos_id") String videoId,
                                     @Field("user_id") String userId,
                                     @Field("comment") String comment,
                                     @Field("comments_id") String commentId);
    @GET("all_replay")
    Call<List<GetCommentsModel>> getAllReply(@Header("API-KEY") String apiKey,
                                                @Query("id") String id);

}
