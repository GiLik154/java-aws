package com.example.demo.domain.entity.passbook.service.sidepage;

import com.example.demo.domain.entity.passbook.service.sidepage.dto.TotalPassBookDto;

public interface TotalPassBookService {
    TotalPassBookDto getTotalPassBookDto(String userId, int year, int month, int pbKindsNum);
}
