package com.example.demo.domain.entity.passbook.service.delete.dto;

import lombok.Getter;

@Getter
public class DateServiceDto {
    private int year;
    private int month;
    private int day;

    public DateServiceDto(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
