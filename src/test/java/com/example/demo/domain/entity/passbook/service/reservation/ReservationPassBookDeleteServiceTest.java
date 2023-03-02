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
class ReservationPassBookDeleteServiceTest {
    private final ReservationPassBookDeleteService reservationPassBookDeleteService;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    ReservationPassBookDeleteServiceTest(ReservationPassBookDeleteService reservationPassBookDeleteService, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.reservationPassBookDeleteService = reservationPassBookDeleteService;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 삭제_정상_작동() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);
        passBookMapper.add(passBook);

        int num = passBookMapper.selectReservationAll(passBook.getUser(), pbKindsNum).get(0).getNum();

        //when
        boolean isDelete = reservationPassBookDeleteService.delete(num, passBook.getUser());

        //then
        Assertions.assertThat(isDelete).isTrue();
    }

    @Test
    void 삭제_정상_비작동_작성자다름() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);
        passBookMapper.add(passBook);

        int num = passBookMapper.selectReservationAll(passBook.getUser(), pbKindsNum).get(0).getNum();

        //when
        boolean isDelete = reservationPassBookDeleteService.delete(num, "test1");

        //then
        Assertions.assertThat(isDelete).isFalse();
    }

    @Test
    void 삭제_정상_비작동_NUM_다름() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);
        passBookMapper.add(passBook);

        //when
        boolean isDelete = reservationPassBookDeleteService.delete(1, passBook.getUser());

        //then
        Assertions.assertThat(isDelete).isFalse();
    }
}