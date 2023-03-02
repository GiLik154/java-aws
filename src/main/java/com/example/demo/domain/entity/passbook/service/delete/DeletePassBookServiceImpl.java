package com.example.demo.domain.entity.passbook.service.delete;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.domain.entity.passbook.service.delete.dto.DateServiceDto;
import com.example.demo.mapper.PassBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DeletePassBookServiceImpl implements DeletePassBookService {
    private final PassBookMapper passBookMapper;

    @Override
    public List<PassBook> getDaily(String userId, DateServiceDto dateServiceDto, int pbKindsNum) {
        return passBookMapper.selectDaily(userId, dateServiceDto.getYear(), dateServiceDto.getMonth(), dateServiceDto.getDay(), pbKindsNum);
    }

    @Override
    public boolean deletePassBook(int num, String userId) {
        return passBookMapper.delete(num, userId) == 1;
    }
}