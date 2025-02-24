package com.example.payment;import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PayOSApi {
    @POST("v2/payment-requests")
    Call<PaymentResponse> createPayment(@Body PaymentRequest request);
}