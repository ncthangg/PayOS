package com.example.payment;

public class PayOSConfig {
    public static final String BASE_URL = "https://api-merchant.payos.vn/";

    // Thay bằng thông tin của bạn từ PayOS
    public static final String API_KEY = "36f4721b-aca8-4684-bbd5-58ccf2e95c3d";
    public static final String CLIENT_KEY = "5e0d52b3-abf3-4916-a5a9-a47eec1cf631";
    public static final String CHECKSUM_KEY = "8eebe5a2a51556ce11134b5ff6ed7c53bc7dc9b270d7e2db94e29c320079260c";

    public static final String RETURN_URL = "https://yourwebsite.com/payment-success";
    public static final String CANCEL_URL = "https://yourwebsite.com/payment-cancel";
}
