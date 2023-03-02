package com.example.demo.domain.entity.passbook.service.reservation;

import com.example.demo.domain.entity.passbook.domain.PassBook;

import java.util.List;

public interface ReservationPassBookMainService {
    List<PassBook> selectAll(String userId, int pbKindsNum);
    List<PassBook> selectList(String userId, int pbKindsNum);

}
