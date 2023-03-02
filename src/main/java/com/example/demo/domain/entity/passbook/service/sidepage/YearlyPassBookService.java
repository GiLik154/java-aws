package com.example.demo.domain.entity.passbook.service.sidepage;

import com.example.demo.domain.entity.passbook.service.sidepage.dto.YearlyPassBookDto;

public interface YearlyPassBookService {
    YearlyPassBookDto getYearlyPassBookDto(String userId, int year, int pbKindsNum);
}
