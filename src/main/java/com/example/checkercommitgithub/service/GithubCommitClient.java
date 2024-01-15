package com.example.checkercommitgithub.service;

import com.example.checkercommitgithub.constant.GithubAPI;
import com.example.checkercommitgithub.constant.repo.GithubAlgorithmRepo;
import com.example.checkercommitgithub.constant.repo.GithubDashBoardRepo;
import com.example.checkercommitgithub.service.dto.CommitUpdate;
import com.example.checkercommitgithub.service.dto.GithubCommitsResponse;
import com.example.checkercommitgithub.service.dto.Readme;
import com.example.checkercommitgithub.service.dto.ReadmeUpdate;
import com.example.checkercommitgithub.util.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class GithubCommitClient {

    @Value("${github.api.key}")
    private String apiKey;

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
                boolean checked = isUpdated(start, end, infos);

                updates.add(new CommitUpdate(value.getUsername(), checked));
            } else {
                throw new IllegalArgumentException();
            }
        }
        return updates;
    }

    private boolean isUpdated(LocalDateTime start, LocalDateTime end, List<GithubCommitsResponse> infos) {
        boolean checked = false;
        for (int j = 0; j < infos.size(); j++) {
            GithubCommitsResponse commit = infos.get(j);
            if (TimeUtil.isTimeWithinRange(start, end, commit.getCommit().getCommitter().getDate().plusHours(9))) {
                checked = true;
                break;
            }
        }
        return checked;
    }

    public void updateReadme(List<CommitUpdate> updates) {
        Readme currentReadmeFile = getReadmeFile();
        String newContent = makeNewContent(updates, currentReadmeFile.getConvertedContent());
        System.out.println(newContent);
        updateReadmeFile(currentReadmeFile, newContent);
    }

    private void updateReadmeFile(Readme readme, String newContent) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("X-GitHub-Api-Version", "2022-11-28");
        ReadmeUpdate dashboardReadme = new ReadmeUpdate("update dashboard", Base64.getEncoder().encodeToString(newContent.getBytes()), readme.getSha());
        HttpEntity<ReadmeUpdate> entity = new HttpEntity<>(dashboardReadme, headers);

        String url = GithubAPI.DOMAIN.getUrl() + GithubAPI.GET_README.getUrl();
        url = url.replace("{owner}", GithubDashBoardRepo.ALGORITHM_DASHBOARD.getUsername());
        url = url.replace("{repo}", GithubDashBoardRepo.ALGORITHM_DASHBOARD.getRepository());
        url = url.replace("{path}", "README.md");
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("OK!!!");
        }
    }

    private String makeNewContent(List<CommitUpdate> updates, String currentReadmeFile) {
        currentReadmeFile = currentReadmeFile.replace("# github-commit-checker\n" +
                "#  ✅ 커밋 체크 대시보드\n", "");
        StringBuilder content = new StringBuilder();
        content.append("# github-commit-checker").append("\n").append("#  ✅ 커밋 체크 대시보드").append("\n\n");
        content.append("---").append("\n").append("# ").append(LocalDate.now()).append("\n\n").append("| 커밋 여부 | 이름 |").append("\n").append("|-----|-----|").append("\n");
        updates.forEach(commitUpdate -> {
            String username = commitUpdate.getUsername();
            content.append("| ").append(commitUpdate.isUpdate() ? "✅" : " ").append(" | ").append(username).append(" |\n");
        });

        content.append("-".repeat(3)).append("\n").append(currentReadmeFile).append("\n");
        return content.toString();
    }

    private Readme getReadmeFile() {
        // Base64로 디코딩
        String url = readReadMeURL();
        ResponseEntity<Readme> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {
        });

        Readme readme = response.getBody();
        // 디코딩된 데이터를 String으로 변환 (필요한 경우)
        return readme;
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
