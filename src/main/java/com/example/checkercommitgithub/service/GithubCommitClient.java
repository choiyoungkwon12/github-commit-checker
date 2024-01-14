package com.example.checkercommitgithub.service;

import com.example.checkercommitgithub.constant.GithubAPI;
import com.example.checkercommitgithub.constant.repo.GithubAlgorithmRepo;
import com.example.checkercommitgithub.constant.repo.GithubDashBoardRepo;
import com.example.checkercommitgithub.service.dto.CommitUpdate;
import com.example.checkercommitgithub.service.dto.GithubCommitsResponse;
import com.example.checkercommitgithub.service.dto.Readme;
import com.example.checkercommitgithub.util.TimeUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class GithubCommitClient {
    private final RestTemplate restTemplate;

    public GithubCommitClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CommitUpdate> getAlgorithmCommitInfo(LocalDateTime start, LocalDateTime end) {
        List<CommitUpdate> updates = new ArrayList<>();
        for (int i = 0; i < GithubAlgorithmRepo.values().length; i++) {
            GithubAlgorithmRepo value = GithubAlgorithmRepo.values()[i];
            String url = makeCommitRequestURL(value);
            ResponseEntity<List<GithubCommitsResponse>> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {
            });

            if (response.getStatusCode().is2xxSuccessful()) {
                List<GithubCommitsResponse> infos = response.getBody();
                boolean checked = false;

                System.out.println(infos.size());
                for (int j = 0; j < infos.size(); j++) {
                    GithubCommitsResponse commit = infos.get(i);
                    System.out.println("name " + commit.getCommit().getCommitter().getName());
                    System.out.println("Date " + commit.getCommit().getCommitter().getDate());
                    System.out.println("message " + commit.getCommit().getMessage());

                    if (TimeUtil.isTimeWithinRange(start, end, commit.getCommit().getCommitter().getDate())) {
                        checked = true;
                        break;
                    }
                }

                updates.add(new CommitUpdate(value.getUsername(), checked));
            } else {
                throw new IllegalArgumentException();
            }
        }
        return updates;
    }

    public void updateReadme(List<CommitUpdate> updates) {
        String currentReadmeFile = getReadmeFile();
        String content = makeNewContent(updates, currentReadmeFile);

        System.out.println(content);
    }

    private String makeNewContent(List<CommitUpdate> updates, String currentReadmeFile) {
        currentReadmeFile = currentReadmeFile.replace("# github-commit-checker\n" +
                "#  ✅ 커밋 체크 대시보드\n", "");
        StringBuilder content = new StringBuilder();
        content.append("# github-commit-checker").append("\n").append("#  ✅ 커밋 체크 대시보드").append("\n\n");
        content.append("---").append("\n").append("# ").append(LocalDate.now()).append("\n").append("| 커밋 여부 | 이름 |").append("\n");
        updates.forEach(commitUpdate -> {
            String username = commitUpdate.getUsername();
            content.append("| ").append(commitUpdate.isUpdate() ? "✅" : " ").append(" | ").append(username).append(" |\n");
        });

        content.append("-".repeat(3)).append("\n").append(currentReadmeFile).append("\n");
        return content.toString();
    }

    private String getReadmeFile() {
        // Base64로 디코딩
        String url = readReadMeURL();
        ResponseEntity<Readme> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {
        });

        Readme body = response.getBody();
        // 디코딩된 데이터를 String으로 변환 (필요한 경우)
        String readmeFileContent = new String(body.getContent());
        System.out.println(readmeFileContent);
        return readmeFileContent;
    }

    private String makeCommitRequestURL(GithubAlgorithmRepo repo) {
        String url = GithubAPI.COMMIT_LIST_URL.getUrl().replace("{owner}", repo.getUsername());
        url = url.replace("{repo}", repo.getRepository());
        return GithubAPI.DOMAIN.getUrl() + url;
    }

    private String readReadMeURL() {
        String url = GithubAPI.GET_README.getUrl().replace("{owner}", GithubDashBoardRepo.ALGORITHM_DASHBOARD.getUsername());
        url = url.replace("{repo}", GithubDashBoardRepo.ALGORITHM_DASHBOARD.getRepository());
        url = url.replace("{path}", "README.md");
        return GithubAPI.DOMAIN.getUrl() + url;
    }
}
