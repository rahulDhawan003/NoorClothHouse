package com.rbr.noorclothhouse.api;

import com.google.gson.annotations.SerializedName;

public class ResponseClass {

    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }
}
