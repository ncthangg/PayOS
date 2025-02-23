package com.example.payment;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayOSService {
    private static PayOSApiService apiService;

    public static PayOSApiService getInstance() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PayOSConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(PayOSApiService.class);
        }
        return apiService;
    }
}
