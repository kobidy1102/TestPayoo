package com.example.testmybasecode.service.model;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {
    @SerializedName("data")
    private String data;

    public String getData() {
        return data;
    }
}
