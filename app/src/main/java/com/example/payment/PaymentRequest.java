package com.example.payment;

public class PaymentRequest {
    private String orderCode;
    private String amount;
    private String description;
    private String returnUrl;
    private String cancelUrl;
    private String signature;

    public PaymentRequest(String orderCode, String amount, String description, String returnUrl, String cancelUrl, String signature) {
        this.orderCode = orderCode;
        this.amount = amount;
        this.description = description;
        this.returnUrl = returnUrl;
        this.cancelUrl = cancelUrl;
        this.signature = signature;
    }
}

