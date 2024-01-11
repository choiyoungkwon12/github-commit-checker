package com.example.checkercommitgithub.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Author {
    private String name;
    private String email;
    private LocalDateTime date;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
