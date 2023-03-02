package com.example.demo.domain.entity.passbook.service.add.dto;

import lombok.Getter;

@Getter
public class AddPassBookServiceDto {
    private String user;
    private String kinds;
    private int price;
    private String content;
    private int year;
    private int month;
    private int day;
    private String reservation;
    private int pbKindsNum;

    public AddPassBookServiceDto(String user, String kinds, int price, String content, int year, int month, int day, String reservation, int pbKindsNum) {
        this.user = user;
        this.kinds = kinds;
        this.price = price;
        this.content = content;
        this.year = year;
        this.month = month;
        this.day = day;
        this.reservation = reservation;
        this.pbKindsNum = pbKindsNum;
    }
}
