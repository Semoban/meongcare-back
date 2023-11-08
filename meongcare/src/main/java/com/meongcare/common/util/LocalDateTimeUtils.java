package com.meongcare.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTimeUtils {

    private static final DateTimeFormatter AM_PM_Formatter = DateTimeFormatter.ofPattern("a hh:mm");
    private static final String PERIOD_DATE_FORMAT = "%02d.%02d ~ %02d.%02d";
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

    public static String createAMPMTime(LocalDateTime dateTime) {
        return dateTime.format(AM_PM_Formatter);
    }

    public static LocalDateTime createLastMonthDateTime(LocalDateTime dateTime) {
        LocalDateTime lastMonthDateTime = dateTime.minusMonths(1);
        return LocalDateTime.of(
                LocalDate.of(lastMonthDateTime.getYear(), lastMonthDateTime.getMonth(), FIRST_DAY_OF_MONTH),
                LocalTime.MIDNIGHT
        );
    }

    public static LocalDateTime createThisMonthDateTime(LocalDateTime dateTime) {
        return LocalDateTime.of(
                LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getMonth().maxLength()),
                LocalTime.MAX
        );
    }

    public static LocalDateTime createThreeWeeksAgoStartDay(LocalDateTime dateTime) {
        LocalDateTime threeWeeksAgoFirstDay = dateTime.minusWeeks(3).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        return LocalDateTime.of(
                LocalDate.from(threeWeeksAgoFirstDay),
                LocalTime.MIDNIGHT
        );
    }

    public static LocalDateTime createThisWeekLastDay(LocalDateTime dateTime) {
        LocalDateTime thisWeekLastDay = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        return LocalDateTime.of(
                LocalDate.from(thisWeekLastDay),
                LocalTime.MAX
        );
    }

    public static String getPeriodDate(LocalDateTime startDay, LocalDateTime lastDay) {
        return String.format(PERIOD_DATE_FORMAT,
                startDay.getMonthValue(), startDay.getDayOfMonth(),
                lastDay.getMonthValue(), lastDay.getDayOfMonth()
        );
    }
}
