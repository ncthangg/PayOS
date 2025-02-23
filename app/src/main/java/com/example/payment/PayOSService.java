package com.example.payment;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayOSService {
    private static PayOSService instance;
    private PayOSApi api;

    private PayOSService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PayOSConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(PayOSApi.class);
    }

    public static PayOSService getInstance() {
        if (instance == null) {
            instance = new PayOSService();
        }
        return instance;
    }

    public Call<PaymentResponse> createPayment(PaymentRequest request) {
        return api.createPayment(request);
    }
}

