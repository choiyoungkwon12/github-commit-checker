package com.example.checkercommitgithub.service.dto;

public class CommitUpdate {
    private final String username;
    private final boolean update;

    public CommitUpdate(String username, boolean update) {
        this.username = username;
        this.update = update;
    }

    public String getUsername() {
        return username;
    }

    public boolean isUpdate() {
        return update;
    }
}
