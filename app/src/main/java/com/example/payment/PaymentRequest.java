package com.example.payment;

public class PaymentRequest {
    private String orderCode;
    private String amount;
    private String description;
    private String returnUrl;
    private String cancelUrl;

    public PaymentRequest(String orderCode, String amount, String description) {
        this.orderCode = orderCode;
        this.amount = amount;
        this.description = description;
        this.returnUrl = PayOSConfig.RETURN_URL;
        this.cancelUrl = PayOSConfig.CANCEL_URL;
    }
}

