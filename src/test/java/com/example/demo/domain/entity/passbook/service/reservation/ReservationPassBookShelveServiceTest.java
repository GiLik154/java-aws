package com.example.demo.domain.entity.passbook.service.reservation;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.mapper.PassBookKindsMapper;
import com.example.demo.mapper.PassBookMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationPassBookShelveServiceTest {

    private final ReservationPassBookShelveService reservationPassBookShelveService;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    ReservationPassBookShelveServiceTest(ReservationPassBookShelveService reservationPassBookShelveService, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.reservationPassBookShelveService = reservationPassBookShelveService;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 다음달로_업데이트() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);
        passBookMapper.add(passBook);

        int num = passBookMapper.selectReservationAll(passBook.getUser(), pbKindsNum).get(0).getNum();

        //when
        boolean isRegister = reservationPassBookShelveService.shelveMonth(num, passBook.getUser());

        //then
        Assertions.assertThat(isRegister).isTrue();
    }

    @Test
    void 다음달로_업데이트_실패_게시물없음() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);
        passBookMapper.add(passBook);

        int num = passBookMapper.selectAll(passBook.getUser(), pbKindsNum).get(0).getNum();

        //when
        boolean isRegister = reservationPassBookShelveService.shelveMonth(num, passBook.getUser());

        //then
        Assertions.assertThat(isRegister).isFalse();
    }
}