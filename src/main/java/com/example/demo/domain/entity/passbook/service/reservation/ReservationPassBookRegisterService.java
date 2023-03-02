package com.example.demo.domain.entity.passbook.service.reservation;

import com.example.demo.domain.entity.passbook.domain.PassBook;
public interface ReservationPassBookRegisterService {
    PassBook selectEach(int num, String userId, int pbKindsNum);
    boolean register(int num, String userId, int pbKindsNum);
}
