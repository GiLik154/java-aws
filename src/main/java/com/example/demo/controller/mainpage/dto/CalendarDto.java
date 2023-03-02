package com.example.demo.controller.mainpage.dto;

import lombok.Getter;

@Getter
public class CalendarDto {
    private int maxDay;
    private int dayOfWeek;
    private String sumYearAndMonth;

    public CalendarDto(int maxDay, int dayOfWeek, String sumYearAndMonth) {
        this.maxDay = maxDay;
        this.dayOfWeek = dayOfWeek;
        this.sumYearAndMonth = sumYearAndMonth;
    }

}
