package com.example.payment;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {
    @SerializedName("checkoutUrl")
    private String checkoutUrl;

    public String getCheckoutUrl() {
        return checkoutUrl;
    }
}


