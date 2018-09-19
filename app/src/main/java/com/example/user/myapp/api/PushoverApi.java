package com.example.user.myapp.api;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PushoverApi {

    @FormUrlEncoded
    @POST("messages.json")
    Single<SendMessageResponse> sendMessage(
            @Field("token") String token,
            @Field("user") String user,
            @Field("message") String message
    );
}
