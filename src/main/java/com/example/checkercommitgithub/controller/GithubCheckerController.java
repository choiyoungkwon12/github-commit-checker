package com.example.checkercommitgithub.controller;

import com.example.checkercommitgithub.service.GithubCommitClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/commit")
@RestController
public class GithubCheckerController {

    private final GithubCommitClient githubCommitClient;

    public GithubCheckerController(GithubCommitClient githubCommitClient) {
        this.githubCommitClient = githubCommitClient;
    }

    @GetMapping("/checker")
    public void checker() {
        githubCommitClient.getCommitList();
    }

    //    @GetMapping("/checker")
    public String findCoffeeBuyer() {
        return "GOOD";
    }
}
