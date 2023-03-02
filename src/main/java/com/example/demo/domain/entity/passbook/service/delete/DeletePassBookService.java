package com.example.demo.domain.entity.passbook.service.delete;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.domain.entity.passbook.service.delete.dto.DateServiceDto;

import java.util.List;

public interface DeletePassBookService {
    List<PassBook> getDaily(String userId, DateServiceDto dateServiceDto, int pbKindsNum);

    boolean deletePassBook(int num, String userId);
}