package com.example.payment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPayment();
            }
        });
    }

    protected void createPayment() {
        int orderCode = 1;
        int amount = 50000;
        String description = "Thanhtoandonhang";
        String cancelURL = PayOSConfig.CANCEL_URL;
        String returnURL = PayOSConfig.RETURN_URL;
        String checkSumKey = PayOSConfig.CHECKSUM_KEY;
        String secretKey = PayOSConfig.SECRET_KEY;
        String signature = PayOSHelper.createSignatureData(amount, cancelURL, description, orderCode, returnURL, secretKey).toString().trim();
        PaymentRequest request = new PaymentRequest(orderCode, amount, description, returnURL, cancelURL, signature);

        Log.d("PAYOS", "Call API: ");

        PayOSService.getInstance().createPayment(request).enqueue(new Callback<PaymentResponse>() {
                @Override
                public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {

                    Log.d("PAYOS", "Response Code: " + response.code());
                    Log.d("PAYOS", "Response Message: " + response.message());
                    Log.d("PAYOS", "Response URL: " + response.raw().request().url());

                    if (response.body() != null) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String jsonResponse = gson.toJson(response.body());
                        Log.d("PAYOS", "Full Response Body: " + jsonResponse);
                    } else {
                        try {
                            Log.e("PAYOS", "Raw Response: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    //if (response.isSuccessful() && response.body() != null && response.body().getCheckoutUrl() != null) {
                        String checkoutUrl = response.body().getCheckoutUrl();
                        Log.d("PAYOS", "Checkout URL: " + checkoutUrl);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(checkoutUrl));
                        startActivity(browserIntent);
                    //} else {
                        if (response.errorBody() != null) {
                            try {
                                Log.e("PAYOS", "Error Description: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("PAYOS", "No errorBody, API return ErrorCode: " + response.code());
                        }
                    //}
                }

                @Override
                public void onFailure(Call<PaymentResponse> call, Throwable t) {
                    Log.e("PAYOS", "Connected Error", t);
                }
            });
    }

}

