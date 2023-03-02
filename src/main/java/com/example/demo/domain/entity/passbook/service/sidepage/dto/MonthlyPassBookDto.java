package com.example.demo.domain.entity.passbook.service.sidepage.dto;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import lombok.Getter;

import java.util.List;

@Getter
public class MonthlyPassBookDto {
    private long depositForMonthly;
    private long withdrawalForMonthly;
    private List<PassBook> maxDepositForMonth;
    private List<PassBook> maxWithdrawalForMonth;

    public MonthlyPassBookDto(long depositForMonthly,
                              long withdrawalForMonthly,
                              List<PassBook> maxDepositForMonth,
                              List<PassBook> maxWithdrawalForMonth) {
        this.depositForMonthly = depositForMonthly;
        this.withdrawalForMonthly = withdrawalForMonthly;
        this.maxDepositForMonth = maxDepositForMonth;
        this.maxWithdrawalForMonth = maxWithdrawalForMonth;
    }
}
