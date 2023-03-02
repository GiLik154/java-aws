package com.example.demo.domain.entity.passbook.service.sidepage;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.domain.entity.passbook.service.sidepage.dto.MonthlyPassBookDto;
import com.example.demo.mapper.PassBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MonthlyPassBookServiceImpl implements MonthlyPassBookService {
    private final PassBookMapper passBookMapper;

    public MonthlyPassBookDto getMonthlyPassBookDto(String userId, int year, int month, int pbKindsNum) {
        return new MonthlyPassBookDto(
                selectDepositForMonthly(userId, year, month, pbKindsNum),
                selectWithdrawalForMonthly(userId, year, month, pbKindsNum),
                selectMaxDepositForMonth(userId, year, month, pbKindsNum),
                selectMaxWithdrawalForMonth(userId, year, month, pbKindsNum));
    }

    private long selectDepositForMonthly(String userId, int year, int month, int pbKindsNum) {
        return passBookMapper.selectDepositForMonthly(userId, year, month, pbKindsNum);
    }

    private long selectWithdrawalForMonthly(String userId, int year, int month, int pbKindsNum) {
        return passBookMapper.selectWithdrawalForMonthly(userId, year, month, pbKindsNum);
    }

    private List<PassBook> selectMaxDepositForMonth(String userId, int year, int month, int pbKindsNum) {
        return checkNullForMap(passBookMapper.selectMaxDepositForMonth(userId, year, month, pbKindsNum));
    }

    private List<PassBook> selectMaxWithdrawalForMonth(String userId, int year, int month, int pbKindsNum) {
        return checkNullForMap(passBookMapper.selectMaxWithdrawalForMonth(userId, year, month, pbKindsNum));
    }

    private List<PassBook> checkNullForMap(List<PassBook> list) {
        if (list.isEmpty()) {
            list = new ArrayList<>();
            list.add(new PassBook(0, "없음"));
        }
        return list;
    }
}
