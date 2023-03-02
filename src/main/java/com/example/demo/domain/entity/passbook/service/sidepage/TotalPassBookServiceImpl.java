package com.example.demo.domain.entity.passbook.service.sidepage;

import com.example.demo.domain.entity.passbook.service.sidepage.dto.TotalPassBookDto;
import com.example.demo.mapper.PassBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TotalPassBookServiceImpl implements TotalPassBookService {
    private final PassBookMapper passBookMapper;

    public TotalPassBookDto getTotalPassBookDto(String userId, int year, int month, int pbKindsNum) {
        return new TotalPassBookDto(
                selectTotalByMonth(userId, year, month, pbKindsNum),
                selectTotalByYear(userId, year, pbKindsNum));
    }

    private long selectTotalByMonth(String userId, int year, int month, int pbKindsNum) {
        return passBookMapper.selectTotalByMonth(userId, year, month, pbKindsNum);
    }

    private long selectTotalByYear(String userId, int year, int pbKindsNum) {
        return passBookMapper.selectTotalByYear(userId, year, pbKindsNum);
    }
}
