package com.example.demo.domain.entity.passbook.service.sidepage;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.domain.entity.passbook.service.sidepage.dto.YearlyPassBookDto;
import com.example.demo.mapper.PassBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class YearlyPassBookServiceImpl implements YearlyPassBookService {

    private final PassBookMapper passBookMapper;

    public YearlyPassBookDto getYearlyPassBookDto(String userId, int year, int pbKindsNum) {
        return new YearlyPassBookDto(
                selectDepositForYearly(userId, year, pbKindsNum),
                selectWithdrawalForYearly(userId, year, pbKindsNum),
                selectMaxDepositForYear(userId, year, pbKindsNum),
                selectMaxWithdrawalForYear(userId, year, pbKindsNum));
    }

    private long selectDepositForYearly(String userId, int year, int pbKindsNum) {
        return passBookMapper.selectDepositForYearly(userId, year, pbKindsNum);
    }

    private long selectWithdrawalForYearly(String userId, int year, int pbKindsNum) {
        return passBookMapper.selectWithdrawalForYearly(userId, year, pbKindsNum);
    }

    private List<PassBook> selectMaxDepositForYear(String userId, int year, int pbKindsNum) {
        return checkNullForMap(passBookMapper.selectMaxDepositForYear(userId, year, pbKindsNum));
    }

    private List<PassBook> selectMaxWithdrawalForYear(String userId, int year, int pbKindsNum) {
        return checkNullForMap(passBookMapper.selectMaxWithdrawalForYear(userId, year, pbKindsNum));
    }

    private List<PassBook> checkNullForMap(List<PassBook> list) {
        if (list.isEmpty()) {
            list = new ArrayList<>();
            list.add(new PassBook(0, "없음"));
        }
        return list;
    }
}
