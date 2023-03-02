package com.example.demo.domain.entity.passbook.service.reservation;

import com.example.demo.mapper.PassBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationPassBookDeleteServiceImpl implements ReservationPassBookDeleteService {
    private final PassBookMapper passBookMapper;

    public boolean delete(int num, String userId) {
        return passBookMapper.delete(num, userId) == 1;
    }
}
