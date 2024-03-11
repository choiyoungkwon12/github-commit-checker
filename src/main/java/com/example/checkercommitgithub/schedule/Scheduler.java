package com.example.checkercommitgithub.schedule;

import com.example.checkercommitgithub.service.GithubDashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Scheduler {
    private final GithubDashboardService githubDashboardService;

    public Scheduler(GithubDashboardService githubDashboardService) {
        this.githubDashboardService = githubDashboardService;
    }

    @Scheduled(cron = "0 0 6 * * *", zone = "Asia/Seoul")
    public void check() {
        log.info("---- check ----");
        githubDashboardService.checkGithubCommit();
    }
}
