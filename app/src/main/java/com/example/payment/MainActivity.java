package com.example.payment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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

    private void createPayment() {
        String checkoutUrl= "";
        String orderCode = "ORDER123";
        String amount = "50000";
        String description = "Thanh toán đơn hàng";
        String cancelURL = PayOSConfig.CANCEL_URL;
        String returnURL = PayOSConfig.RETURN_URL;
        String checkSumKey = PayOSConfig.CHECKSUM_KEY;
        String signature = PayOSHelper.createSignatureData(amount, cancelURL, description, orderCode, returnURL, checkSumKey).toString();

        PaymentRequest request = new PaymentRequest(orderCode, amount, description, returnURL, cancelURL, signature);

        Log.d("PAYOS", "Call API: ");

        Log.d("PAYOS", "PayOSService instance: " + PayOSService.getInstance());

            PayOSService.getInstance().createPayment(request).enqueue(new Callback<PaymentResponse>() {
                @Override
                public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String checkoutUrl = response.body().getCheckoutUrl();
                        Log.d("PAYOS", "Checkout URL: " + checkoutUrl);

                        // Mở trình duyệt để thanh toán
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(checkoutUrl));
                        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(browserIntent);
                    } else {
                        try {
                            Log.e("PAYOS", "Lỗi response: " + response.errorBody().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PaymentResponse> call, Throwable t) {
                    Log.e("PAYOS", "Lỗi kết nối", t);
                }
            });

    }
}

