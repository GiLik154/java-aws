package com.example.demo.controller.vo;


import com.example.demo.domain.entity.passbook.service.delete.dto.DateServiceDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

public class DateVo {

    private final String date;

    public DateVo(String date) {
        LocalDate now = LocalDate.now();

        if (date.isBlank()) {
            date = String.valueOf(now);
        }

        this.date = date;
    }

    private int[] splitDate() {
        return Arrays.stream(date.split("-"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public int getYear() {
        return splitDate()[0];
    }

    public int getMonth() {
        return splitDate()[1];
    }

    public int getDay() {
        return splitDate()[2];
    }

    public String getDate() {
        return date;
    }

    public DateServiceDto convertedDto() {
        return new DateServiceDto(this.getYear(), this.getMonth(), this.getDay());
    }

    public int findMaxDay() {
        LocalDate findMaxDay = LocalDate.parse(this.date);
        return findMaxDay.lengthOfMonth();
    }

    public int findFirstDayOfWeek() {
        LocalDate findFirstDay = LocalDate.of(getYear(), getMonth(), 2);
        DayOfWeek dayOfWeek = findFirstDay.getDayOfWeek();
        return dayOfWeek.getValue();
    }

    public String sumYearAndMonth() {
        return getYear() + "-" + getMonth();
    }
}