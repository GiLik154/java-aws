package com.example.demo.domain.entity.passbook.service.reservation;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.mapper.PassBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationPassBookMainServiceImpl implements ReservationPassBookMainService {
    private final PassBookMapper passBookMapper;

    public List<PassBook> selectAll(String userId, int pbKindsNum) {
        return passBookMapper.selectReservationAll(userId, pbKindsNum);
    }

    public List<PassBook> selectList(String userId, int pbKindsNum) {
        LocalDate now = LocalDate.now();
        return passBookMapper.selectReservation(userId, now.getMonthValue(), pbKindsNum);
    }

}
