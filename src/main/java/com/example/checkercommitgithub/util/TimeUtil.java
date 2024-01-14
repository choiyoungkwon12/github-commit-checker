package com.example.checkercommitgithub.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

    // 작일 오전 6시
    public static LocalDateTime getStartTime() {
        return LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS).plusHours(6);
    }

    // 금일 오전 6시
    public static LocalDateTime getEndTime() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(6);
    }

    public static boolean isTimeWithinRange(LocalDateTime start, LocalDateTime end, LocalDateTime time) {
        return !time.isBefore(start) && !time.isAfter(end);
    }
}
