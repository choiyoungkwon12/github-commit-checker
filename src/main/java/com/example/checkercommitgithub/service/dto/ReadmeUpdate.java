package com.example.checkercommitgithub.service.dto;

public class ReadmeUpdate {
    private final String message;
    private final String content;
    private final String sha;

    public ReadmeUpdate(String message, String content, String sha) {
        this.message = message;
        this.content = content;
        this.sha = sha;
    }

    public String getSha() {
        return sha;
    }

    public String getMessage() {
        return message;
    }

    public String getContent() {
        return content;
    }
}
