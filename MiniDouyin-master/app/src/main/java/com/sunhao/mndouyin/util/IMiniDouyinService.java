package com.sunhao.mndouyin.util;

import com.sunhao.mndouyin.beans.FeedResponse;
import com.sunhao.mndouyin.beans.PostVideoResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface IMiniDouyinService {

    @Multipart
    @POST("minidouyin/video")
    Call<PostVideoResponse> postVideo(
            @Query("student_id") String studentId, @Query("user_name") String username,
            @Part MultipartBody.Part coverImage,
            @Part MultipartBody.Part video
    );


    @GET("minidouyin/feed")
    Call<FeedResponse> feed();
}
