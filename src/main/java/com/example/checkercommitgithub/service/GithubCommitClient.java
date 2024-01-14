package com.example.checkercommitgithub.service;

import com.example.checkercommitgithub.constant.AlgorithmRepo;
import com.example.checkercommitgithub.service.dto.GithubCommitsResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

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
        for (int i = 0; i < AlgorithmRepo.values().length; i++) {
            AlgorithmRepo value = AlgorithmRepo.values()[i];
            String url = makeCommitRequestURL(value);
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
    }
    /*
public class UpdateReadme {
    public static void main(String[] args) {
        String apiUrl = "https://api.github.com/repos/username/repository/commits"; // GitHub API URL
        RestTemplate restTemplate = new RestTemplate();
        CommitInfo[] commits = restTemplate.getForObject(apiUrl, CommitInfo[].class);

        Set<String> commitAuthors = new HashSet<>();
        LocalDateTime yesterday6am = LocalDateTime.now().minusDays(1).withHour(6).withMinute(0).withSecond(0);
        LocalDateTime today6am = LocalDateTime.now().withHour(6).withMinute(0).withSecond(0);

        for (CommitInfo commit : commits) {
            LocalDateTime commitDate = LocalDateTime.parse(commit.getCommit().getAuthor().getDate(), DateTimeFormatter.ISO_DATE_TIME);
            if (commitDate.isAfter(yesterday6am) && commitDate.isBefore(today6am)) {
                commitAuthors.add(commit.getCommit().getAuthor().getName());
            }
        }

        updateReadme(commitAuthors, yesterday6am);
    }

    private static void updateReadme(Set<String> authors, LocalDateTime date) {
        String readmeFile = "path/to/README.md"; // README 파일 경로
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(formatter);

        StringBuilder content = new StringBuilder();
        content.append("### ").append(dateString).append(" (commit date)\n\n");
        content.append("| 커밋 여부 | 이름 |\n");
        content.append("|------|-----|\n");

        // 예시로 추가된 이름들
        String[] allAuthors = {"choiyoungkwon12", "jack", "kim", "lee"};
        for (String author : allAuthors) {
            content.append("| ").append(authors.contains(author) ? "✅" : " ").append(" | ").append(author).append(" |\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(readmeFile, true))) {
            writer.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

     */
    private static void updateReadme(Set<String> authors, LocalDateTime date) {
        String readmeFile = "path/to/README.md"; // README 파일 경로
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(formatter);

        StringBuilder content = new StringBuilder();
        content.append("### ").append(dateString).append(" (commit date)\n\n");
        content.append("| 커밋 여부 | 이름 |\n");
        content.append("|------|-----|\n");

        // 예시로 추가된 이름들
        String[] allAuthors = {"choiyoungkwon12", "jack", "kim", "lee"};
        for (String author : allAuthors) {
            content.append("| ").append(authors.contains(author) ? "✅" : " ").append(" | ").append(author).append(" |\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(readmeFile, true))) {
            writer.write(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String makeCommitRequestURL(AlgorithmRepo repo) {
        String url = COMMIT_LIST_URL.replace("{owner}", repo.getUsername());
        url = url.replace("{repo}", repo.getRepository());
        return GITHUB + url;
    }
}
