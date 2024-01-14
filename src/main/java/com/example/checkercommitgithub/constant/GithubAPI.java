package com.example.checkercommitgithub.constant;

public enum GithubAPI {
    DOMAIN("https://api.github.com"),
    COMMIT_LIST_URL("/repos/{owner}/{repo}/commits"),
    GET_A_COMMIT_URL("/repos/{owner}/{repo}/commits/{BRANCH}"),
    GET_README("/repos/{owner}/{repo}/contents/{path}")  // 리드미 파일 얻는 api
    ;
    private String url;

    GithubAPI(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
