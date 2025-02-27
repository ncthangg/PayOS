package com.example.payment;

public class PaymentRequest {
    private int orderCode;
    private int amount;
    private String description;
    private String returnUrl;
    private String cancelUrl;
    private String signature;

    public PaymentRequest(int orderCode, int amount, String description, String returnUrl, String cancelUrl, String signature) {
        this.orderCode = orderCode;
        this.amount = amount;
        this.description = description;
        this.returnUrl = returnUrl;
        this.cancelUrl = cancelUrl;
        this.signature = signature;
    }
}

