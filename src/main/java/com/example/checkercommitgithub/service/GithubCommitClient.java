package com.example.checkercommitgithub.service;

import com.example.checkercommitgithub.service.dto.CommitInfo;
import com.example.checkercommitgithub.service.dto.GithubCommitsResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/*
오전 6시 배치
-> 전날 6시 ~ 오늘 오전 6시 -> 전날 꺼로 커밋 기록
 */
@Service
public class GithubCommitClient {
    private final RestTemplate restTemplate;

    private static final String GITHUB = "https://api.github.com";
    private static String COMMIT_LIST_URL = "/repos/{owner}/{repo}/commits";
    private static final String GET_A_COMMIT_URL = "/repos/{owner}/{repo}/commits/{BRANCH}}";

    public GithubCommitClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getCommitList() {
        String url = makeCommitRequestURL();
        ResponseEntity<List<GithubCommitsResponse>> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {
        });

        if (response.getStatusCode().is2xxSuccessful()) {
            List<GithubCommitsResponse> infos = response.getBody();
            assert infos != null;
            System.out.println(infos.size());
            infos.forEach(info -> {
                System.out.println("-".repeat(10));
                System.out.println("name " + info.getCommit().getCommitter().getName());
                System.out.println("Date " + info.getCommit().getCommitter().getDate());
                System.out.println("message " + info.getCommit().getMessage());
                System.out.println("-".repeat(10));
            });
        }


    }

    private String makeCommitRequestURL() {
        String url = COMMIT_LIST_URL.replace("{owner}", "choiyoungkwon12");
        url = url.replace("{repo}", "github-commit-checker");
        return GITHUB + url;
    }
}
