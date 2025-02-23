package com.example.payment;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class PayOSHelper {
    public static String computeHmacSha256(String data, String secretKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(data.getBytes());

            // Chuyển đổi byte[] thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo HMAC-SHA256", e);
        }
    }

    public static Map<String, Object> createSignatureData(String amount, String cancelUrl,
                                                          String description, String orderCode,
                                                          String returnUrl, String checksumKey) {
        Map<String, Object> signatureData = new HashMap<>();
        signatureData.put("amount", amount);
        signatureData.put("cancelUrl", cancelUrl);
        signatureData.put("description", description);
        signatureData.put("orderCode", orderCode);
        signatureData.put("returnUrl", returnUrl);

        // Sắp xếp key theo thứ tự alphabet
        SortedMap<String, Object> sortedSignatureData = new TreeMap<>(signatureData);

        // Tạo chuỗi query string để ký
        StringBuilder dataForSignature = new StringBuilder();
        for (Map.Entry<String, Object> entry : sortedSignatureData.entrySet()) {
            if (dataForSignature.length() > 0) {
                dataForSignature.append("&");
            }
            dataForSignature.append(entry.getKey()).append("=").append(entry.getValue());
        }

        // Tạo chữ ký
        String signature = PayOSHelper.computeHmacSha256(dataForSignature.toString(), checksumKey);
        signatureData.put("signature", signature); // Gắn chữ ký vào request

        return signatureData;
    }
}
