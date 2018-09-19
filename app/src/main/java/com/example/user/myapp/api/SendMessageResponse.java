package com.example.user.myapp.api;

import com.google.gson.annotations.SerializedName;

public class SendMessageResponse {

    @SerializedName("status")
    public int status;
    @SerializedName("request")
    public String request;

}

