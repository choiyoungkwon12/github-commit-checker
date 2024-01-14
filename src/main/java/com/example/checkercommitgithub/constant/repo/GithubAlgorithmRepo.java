package com.example.checkercommitgithub.constant.repo;

public enum GithubAlgorithmRepo {
    NED("choiyoungkwon12", "Algorithm-Practice"),
    JACK("KimHaKyeon95", "code_test"),
    CLYDE("Valium-K", "algorithm"),
    DEVIN("zlarbals", "Algorithm_Practice"),
    ;
    private final String username;
    private final String repository;

    GithubAlgorithmRepo(String username, String repository) {
        this.username = username;
        this.repository = repository;
    }


    public String getUsername() {
        return username;
    }

    public String getRepository() {
        return repository;
    }
}
