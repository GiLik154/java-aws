package com.example.demo.domain.entity.passbook.service.mainpage;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.mapper.PassBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MainPageServiceImpl implements MainPageService {
    private final PassBookMapper passBookMapper;

    public boolean checkReservation(String userId, int pbKindsNum) {
        LocalDate now = LocalDate.now();
        List<PassBook> list = passBookMapper.selectReservation(userId, now.getMonthValue(), pbKindsNum);
        return !ObjectUtils.isEmpty(list);
    }
    public List<PassBook> selectTotalByDayList(String userId, int year, int month, int pbKindsNum) {
        return passBookMapper.selectTotalByDayList(userId, year, month, pbKindsNum);
    }
}
