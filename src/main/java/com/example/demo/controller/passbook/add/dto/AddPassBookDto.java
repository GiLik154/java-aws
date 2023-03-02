package com.example.demo.controller.passbook.add.dto;

import com.example.demo.domain.entity.passbook.service.add.dto.AddPassBookServiceDto;

public class AddPassBookDto {
    private String kinds;
    private int price;
    private String content;
    private String reservation;

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public void setPrice(String price) {
        this.price = Integer.parseInt(price.replace(",", ""));
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    public AddPassBookServiceDto convertServiceDto(String user, int year, int month, int day, int pbKindsNum) {
        return new AddPassBookServiceDto(user, this.kinds, this.price, this.content, year, month, day, this.reservation, pbKindsNum);
    }
}
