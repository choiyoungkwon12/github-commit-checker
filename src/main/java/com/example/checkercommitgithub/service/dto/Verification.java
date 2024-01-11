package com.example.checkercommitgithub.service.dto;

public class Verification {
    private boolean verified;
    private String reason;
    private String signature;
    private String payload;

    public boolean isVerified() {
        return verified;
    }

    public String getReason() {
        return reason;
    }

    public String getSignature() {
        return signature;
    }

    public String getPayload() {
        return payload;
    }
}
