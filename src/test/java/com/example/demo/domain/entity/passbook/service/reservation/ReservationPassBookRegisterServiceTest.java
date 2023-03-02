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
class ReservationPassBookRegisterServiceTest {
    private final ReservationPassBookRegisterService reservationPassBookRegisterService;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    public ReservationPassBookRegisterServiceTest(ReservationPassBookRegisterService reservationPassBookRegisterService, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.reservationPassBookRegisterService = reservationPassBookRegisterService;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 개별_목록_츨력() {
        //given
        LocalDate now = LocalDate.now();

        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);

        passBookMapper.add(passBook);
        PassBook testPassBook = passBookMapper.selectAll(passBook.getUser(), pbKindsNum).get(0);

        //when
        PassBook checkPassBook = reservationPassBookRegisterService.selectEach(testPassBook.getNum(), passBook.getUser(), pbKindsNum);

        //then
        Assertions.assertThat(checkPassBook).isNotNull();
    }

    @Test
    void 개별_목록_츨력_게시물없음() {
        //given
        LocalDate now = LocalDate.now();

        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);
        PassBook testPassBook = passBookMapper.selectAll(passBook.getUser(), pbKindsNum).get(0);

        //when
        PassBook checkPassBook = reservationPassBookRegisterService.selectEach(testPassBook.getNum(), passBook.getUser(), pbKindsNum);

        //then
        Assertions.assertThat(checkPassBook).isNull();
    }

    @Test
    void 예약을_일반으로_등록() {
        //given
        LocalDate now = LocalDate.now();

        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);

        passBookMapper.add(passBook);

        PassBook testPassBook = passBookMapper.selectAll(passBook.getUser(), pbKindsNum).get(0);

        //when
        boolean isRegister = reservationPassBookRegisterService.register(testPassBook.getNum(), passBook.getUser(), pbKindsNum);

        //then
        Assertions.assertThat(isRegister).isTrue();
    }
}