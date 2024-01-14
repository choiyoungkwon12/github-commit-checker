package com.example.checkercommitgithub.service.dto;

public class Readme {
    private String sha;
    private String owner;
    private byte[] content;

    public byte[] getContent() {
        return content;
    }

    public String getSha() {
        return sha;
    }

    public String getOwner() {
        return owner;
    }
}
