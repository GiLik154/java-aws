package com.example.demo.domain.entity.passbook.domain;


import lombok.Getter;

@Getter
public class PassBook {

    private int num;
    private String user;
    private String kinds;
    private long price;
    private String content;
    private int year;
    private int month;
    private int day;
    private int pbKindsNum;
    private String reservation;

    public PassBook() {
        super();
    }

    public PassBook(String user, String kinds, long price, String content, int year, int month, int day, String reservation, int pbKindsNum) {
        this.user = user;
        this.kinds = kinds;
        this.price = makeNegativePrice(price);
        this.content = content;
        this.year = year;
        this.month = month;
        this.day = day;
        this.reservation = reservation;
        this.pbKindsNum = pbKindsNum;
    }

    public PassBook(long price, String content) {
        this.price = price;
        this.content = content;
    }

    private long makeNegativePrice(long price) {
        if (this.kinds.equals("출금")) {
            return price * -1;
        }
        return price;
    }
}


