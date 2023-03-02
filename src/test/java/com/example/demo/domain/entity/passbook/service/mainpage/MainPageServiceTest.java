package com.example.demo.domain.entity.passbook.service.mainpage;

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
class MainPageServiceTest {
    private final MainPageService mainPageService;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    MainPageServiceTest(MainPageService mainPageService, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.mainPageService = mainPageService;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 예약_확인_정상작동() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000
                , "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        boolean isCheck = mainPageService.checkReservation("test", pbKindsNum);

        //then
        assertThat(isCheck).isTrue();
    }

    @Test
    void 예약_확인_오류_게시물없음() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        boolean isCheck = mainPageService.checkReservation("test", pbKindsNum);

        //then
        assertThat(isCheck).isFalse();
    }


    @Test
    void 일별_합계_출력() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        PassBook testPassbook = mainPageService.selectTotalByDayList("test", now.getYear(), now.getMonthValue(), pbKindsNum).get(0);

        //then
        assertThat(testPassbook).isNotNull();
    }

    @Test
    void 일별_합계_오류_게시물없음() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        List<PassBook> totalByDayList = mainPageService.selectTotalByDayList("test", now.getYear(), now.getMonthValue(),pbKindsNum);

        //then
        assertTrue(totalByDayList.isEmpty());
    }
}