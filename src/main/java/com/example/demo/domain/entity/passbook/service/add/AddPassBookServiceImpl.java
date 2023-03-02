package com.example.demo.domain.entity.passbook.service.add;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.domain.entity.passbook.service.add.dto.AddPassBookServiceDto;
import com.example.demo.mapper.PassBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddPassBookServiceImpl implements AddPassBookService {
    private final PassBookMapper passBookMapper;

    public boolean add(AddPassBookServiceDto addPassBookServiceDto) {
        PassBook passBook = new PassBook(
                addPassBookServiceDto.getUser(),
                addPassBookServiceDto.getKinds(),
                addPassBookServiceDto.getPrice(),
                addPassBookServiceDto.getContent(),
                addPassBookServiceDto.getYear(),
                addPassBookServiceDto.getMonth(),
                addPassBookServiceDto.getDay(),
                addPassBookServiceDto.getReservation(),
                addPassBookServiceDto.getPbKindsNum()
        );
        return passBookMapper.add(passBook) == 1;
    }
}
