package com.example.user.myapp.api;

import com.example.user.myapp.Constants;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class ApiClient {

    private PushoverApi pushoverApi;

    @Inject
    ApiClient(Retrofit retrofit) {
        pushoverApi = retrofit.create(PushoverApi.class);
    }

    public Single<SendMessageResponse> sendMessageResponseSingle(String userKey, String message) {
        return pushoverApi.sendMessage(Constants.TOKEN, userKey, message);
    }
}
