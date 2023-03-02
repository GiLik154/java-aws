package com.example.demo.domain.entity.passbook.service.reservation;

import com.example.demo.mapper.PassBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationPassBookShelveServiceImpl implements ReservationPassBookShelveService {
    private final PassBookMapper passBookMapper;

    public boolean shelveMonth(int num, String userId) {
        LocalDate now = LocalDate.now();
        int nextMonth = now.plusMonths(1).getMonthValue();
        return passBookMapper.updateReservation(num, userId, nextMonth) == 1;
    }
}
