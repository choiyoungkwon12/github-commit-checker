package com.example.checkercommitgithub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CheckerCommitGithubApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckerCommitGithubApplication.class, args);
    }

}
