package com.example.payment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PayOSApiService {
    @Headers({
            "Content-Type: application/json",
            "x-api-key: " + PayOSConfig.API_KEY
    })
    @POST("payments")
    Call<PaymentResponse> createPayment(@Body PaymentRequest request);
}

