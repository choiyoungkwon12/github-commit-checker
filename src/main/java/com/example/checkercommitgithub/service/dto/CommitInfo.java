package com.example.checkercommitgithub.service.dto;

public class CommitInfo {
    private Author author;
    private Author committer;
    private String message;
    private Tree tree;
    private String url;
    private int commentCount;
    private Verification verification;

    public Author getAuthor() {
        return author;
    }

    public Author getCommitter() {
        return committer;
    }

    public String getMessage() {
        return message;
    }

    public Tree getTree() {
        return tree;
    }

    public String getUrl() {
        return url;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public Verification getVerification() {
        return verification;
    }
}
