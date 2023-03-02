package com.example.demo.domain.entity.passbook.service.reservation;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.mapper.PassBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationPassBookRegisterServiceImpl implements ReservationPassBookRegisterService {
    private final PassBookMapper passBookMapper;
    private final ReservationPassBookShelveService reservationPassBookShelveService;

    @Override
    public PassBook selectEach(int num, String userId, int pnNum) {
        return passBookMapper.selectEachReservationList(num, userId, pnNum);
    }

    @Override
    public boolean register(int num, String userId, int pnNum) {
        if (add(num, userId, pnNum)) {
            return reservationPassBookShelveService.shelveMonth(num, userId);
        }
        return false;
    }

    private boolean add(int num, String userId, int pnNum) {
        return passBookMapper.addReservation(passBookMapper.selectEachReservationList(num, userId, pnNum)) == 1;
    }

}
