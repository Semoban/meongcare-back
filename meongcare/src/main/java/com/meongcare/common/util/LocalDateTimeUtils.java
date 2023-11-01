package com.meongcare.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtils {

    private static final DateTimeFormatter AM_PM_Formatter = DateTimeFormatter.ofPattern("a hh:mm");

    public static LocalDateTime createNowMidnight(LocalDateTime localDateTime) {
        return LocalDateTime.of(
                LocalDate.from(localDateTime),
                LocalTime.MIDNIGHT
        );
    }

    public static LocalDateTime createNextMidnight(LocalDateTime localDateTime) {
        return LocalDateTime.of(
                LocalDate.from(localDateTime.plusDays(1)),
                LocalTime.MIDNIGHT
        );
    }

    public static String createAMPMTime(LocalDateTime localDateTime) {
        return localDateTime.format(AM_PM_Formatter);
    }
}
