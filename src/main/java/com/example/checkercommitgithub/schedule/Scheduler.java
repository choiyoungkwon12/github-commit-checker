package com.example.checkercommitgithub.schedule;

import com.example.checkercommitgithub.service.GithubDashboardService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private final GithubDashboardService githubDashboardService;

    public Scheduler(GithubDashboardService githubDashboardService) {
        this.githubDashboardService = githubDashboardService;
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void check() {
        githubDashboardService.checkGithubCommit();
    }
}
