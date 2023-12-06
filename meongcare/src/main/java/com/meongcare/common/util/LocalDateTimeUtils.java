package com.meongcare.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateTimeUtils {

    private static final DateTimeFormatter AM_PM_Formatter = DateTimeFormatter.ofPattern("a hh:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
    private static final int FIRST_DAY_OF_MONTH = 1;

    public static LocalDateTime createNowMidnight(LocalDateTime dateTime) {
        return LocalDateTime.of(
                LocalDate.from(dateTime),
                LocalTime.MIDNIGHT
        );
    }

    public static LocalDateTime createNextMidnight(LocalDateTime dateTime) {
        return LocalDateTime.of(
                LocalDate.from(dateTime.plusDays(1)),
                LocalTime.MIDNIGHT
        );
    }

    public static String createAMPMTime(LocalTime dateTime) {
        return dateTime.format(AM_PM_Formatter);
    }

    public static LocalTime createNowWithZeroSecond() {
        return LocalTime.now().withSecond(0);
    }

    public static LocalTime createFiftyNineSecondsLater(LocalTime now) {
        return now.plusSeconds(59);
    }

    public static LocalDate createLastMonthFirstDayDate(LocalDate date) {
        LocalDate lastMonthDate = date.minusMonths(1);
        return LocalDate.of(lastMonthDate.getYear(), lastMonthDate.getMonth(), FIRST_DAY_OF_MONTH);
    }

    public static LocalDate createThisMonthLastDayDate(LocalDate date) {
        return LocalDate.of(date.getYear(), date.getMonth(), date.getMonth().maxLength());
    }

    public static LocalDate createThreeWeeksAgoStartDay(LocalDate date) {
        return date.minusWeeks(3).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
    }

    public static LocalDate createThisWeekLastDay(LocalDate date) {
        return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
    }

    public static long getBetweenDays(LocalDate startDay, LocalDate lastDay) {
        return Math.abs(ChronoUnit.DAYS.between(startDay, lastDay));
    }

    public static String getDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }
}
