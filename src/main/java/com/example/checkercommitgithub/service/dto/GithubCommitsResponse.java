package com.example.checkercommitgithub.service.dto;

public class GithubCommitsResponse {
    private String sha;
    private String nodeId;
    private CommitInfo commit;
    private String url;
    private String htmlUrl;

    public String getSha() {
        return sha;
    }

    public String getNodeId() {
        return nodeId;
    }

    public CommitInfo getCommit() {
        return commit;
    }

    public String getUrl() {
        return url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }
}
