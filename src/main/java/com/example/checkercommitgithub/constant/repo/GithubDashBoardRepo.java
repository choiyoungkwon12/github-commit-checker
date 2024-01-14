package com.example.checkercommitgithub.constant.repo;

public enum GithubDashBoardRepo {
    ALGORITHM_DASHBOARD("choiyoungkwon12", "github-commit-checker");

    GithubDashBoardRepo(String username, String repository) {
        this.username = username;
        this.repository = repository;
    }

    private String username;
    private String repository;

    public String getUsername() {
        return username;
    }

    public String getRepository() {
        return repository;
    }
}
