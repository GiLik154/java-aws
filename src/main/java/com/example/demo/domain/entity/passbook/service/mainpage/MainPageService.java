package com.example.demo.domain.entity.passbook.service.mainpage;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import java.util.List;

public interface MainPageService {
    boolean checkReservation(String userId, int pbKindsNum);

    List<PassBook> selectTotalByDayList(String userId, int year, int month, int pbKindsNum);
}
