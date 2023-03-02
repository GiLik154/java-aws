package com.example.demo.domain.entity.passbook.service.reservation;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.mapper.PassBookKindsMapper;
import com.example.demo.mapper.PassBookMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationPassBookMainServiceTest {
    private final ReservationPassBookMainService reservationPassBookMainService;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    public ReservationPassBookMainServiceTest(ReservationPassBookMainService reservationPassBookMainService, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.reservationPassBookMainService = reservationPassBookMainService;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 전체_출력() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        String testContent = reservationPassBookMainService.selectAll(passBook.getUser(), pbKindsNum).get(0).getContent();

        //then
        assertThat(testContent).isEqualTo("테스트 입금");
    }

    @Test
    void 전체_출력_게시물없음() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        List<PassBook> reservationList =reservationPassBookMainService.selectAll(passBook.getUser(), pbKindsNum);

        //then
        assertTrue(reservationList.isEmpty());
    }

    @Test
    void 이번달_목록_출력() {
        //given
        LocalDate now = LocalDate.now();

        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        String testContent = reservationPassBookMainService.selectAll(passBook.getUser(), pbKindsNum).get(0).getContent();

        //then
        assertThat(testContent).isEqualTo("테스트 입금");
    }

    @Test
    void 이번달_목록_출력_게시물없음() {
        //given
        LocalDate now = LocalDate.now();

        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        List<PassBook> reservationList =reservationPassBookMainService.selectAll(passBook.getUser(), pbKindsNum);

        //then
        assertTrue(reservationList.isEmpty());
    }

}