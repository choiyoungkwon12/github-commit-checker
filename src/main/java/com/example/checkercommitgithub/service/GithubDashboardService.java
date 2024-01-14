package com.example.checkercommitgithub.service;


import com.example.checkercommitgithub.service.dto.CommitUpdate;
import com.example.checkercommitgithub.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubDashboardService {

    private final GithubCommitClient githubCommitClient;

    public GithubDashboardService(GithubCommitClient githubCommitClient) {
        this.githubCommitClient = githubCommitClient;
    }

    public void checkGithubCommit() {
        List<CommitUpdate> updates = githubCommitClient.getAlgorithmCommitInfo(TimeUtil.getStartTime(), TimeUtil.getEndTime());
        githubCommitClient.updateReadme(updates);
    }
}
