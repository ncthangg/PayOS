package com.example.payment;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayOSService {
    private static PayOSService instance;
    private PayOSApi api;

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .header("x-client-id", PayOSConfig.CLIENT_KEY)
                            .header("x-api-key", PayOSConfig.API_KEY)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                })
                .build();
    }


    private PayOSService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PayOSConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
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

