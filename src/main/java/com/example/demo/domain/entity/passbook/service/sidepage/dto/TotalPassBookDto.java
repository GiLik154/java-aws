package com.example.demo.domain.entity.passbook.service.sidepage.dto;

import lombok.Getter;

@Getter
public class TotalPassBookDto {
    private long totalByMonth;
    private long totalByYear;

    public TotalPassBookDto(long totalByMonth,
                            long totalByYear) {
        this.totalByMonth = totalByMonth;
        this.totalByYear = totalByYear;
    }
}
