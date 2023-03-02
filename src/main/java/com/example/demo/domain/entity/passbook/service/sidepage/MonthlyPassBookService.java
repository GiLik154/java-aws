package com.example.demo.domain.entity.passbook.service.sidepage;
import com.example.demo.domain.entity.passbook.service.sidepage.dto.MonthlyPassBookDto;

public interface MonthlyPassBookService {
    MonthlyPassBookDto getMonthlyPassBookDto(String userId, int year, int month, int pbKindsNum);
}
