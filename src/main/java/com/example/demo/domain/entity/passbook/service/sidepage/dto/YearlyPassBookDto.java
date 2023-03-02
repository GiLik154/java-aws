package com.example.demo.domain.entity.passbook.service.sidepage.dto;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import lombok.Getter;

import java.util.List;

@Getter
public class YearlyPassBookDto {

    long depositForYearly;
    long withdrawalForYearly;
    List<PassBook> maxDepositForYear;
    List<PassBook> maxWithdrawalForYear;

    public YearlyPassBookDto(long depositForYearly,
                             long withdrawalForYearly,
                             List<PassBook> maxDepositForYear,
                             List<PassBook> maxWithdrawalForYear) {
        this.depositForYearly = depositForYearly;
        this.withdrawalForYearly = withdrawalForYearly;
        this.maxDepositForYear = maxDepositForYear;
        this.maxWithdrawalForYear = maxWithdrawalForYear;
    }
}
