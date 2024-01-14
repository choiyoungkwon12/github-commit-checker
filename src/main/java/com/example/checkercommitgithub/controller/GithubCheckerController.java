package com.example.checkercommitgithub.controller;

import com.example.checkercommitgithub.service.GithubDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/commit")
@RestController
public class GithubCheckerController {

    private final GithubDashboardService githubDashboardService;

    public GithubCheckerController(GithubDashboardService githubDashboardService) {
        this.githubDashboardService = githubDashboardService;
    }


    @GetMapping("/checker")
    public void checker() {
        githubDashboardService.checkGithubCommit();
    }

    //    @GetMapping("/checker")
    public String findCoffeeBuyer() {
        return "GOOD";
    }
}
