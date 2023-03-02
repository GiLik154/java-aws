package com.example.demo.domain.entity.passbook.service.delete;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.mapper.PassBookKindsMapper;
import com.example.demo.mapper.PassBookMapper;
import com.example.demo.domain.entity.passbook.service.delete.dto.DateServiceDto;
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
class DeletePassBookServiceTest {
    private final DeletePassBookService deletePassBookService;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    DeletePassBookServiceTest(DeletePassBookService deletePassBookService, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.deletePassBookService = deletePassBookService;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 하루_단위_목록_출력_정상작동() {
        //given
        LocalDate now = LocalDate.now();
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        DateServiceDto dateServiceDto = new DateServiceDto(now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        //when
        String testContent = deletePassBookService.getDaily(passBook.getUser(), dateServiceDto, pbKindsNum).get(0).getContent();

        //then
        assertThat(testContent).isEqualTo("테스트 입금");
    }

    @Test
    void 하루_단위_목록_출력_비정상작동_작성자다름() {
        //given
        LocalDate now = LocalDate.now();
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        DateServiceDto dateServiceDto = new DateServiceDto(now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        //when
        List<PassBook> dailyList = deletePassBookService.getDaily("test1", dateServiceDto, pbKindsNum);

        //then
        assertTrue(dailyList.isEmpty());
    }

    @Test
    void 삭제_정상_작동() {
        //given
        LocalDate now = LocalDate.now();
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        PassBook testPassBook = passBookMapper.selectAll(passBook.getUser(), pbKindsNum).get(0);

        //when
        boolean isDelete = deletePassBookService.deletePassBook(testPassBook.getNum(), passBook.getUser());

        //then
        assertThat(isDelete).isTrue();
    }

    @Test
    void 삭제_정상_비작동_작성자다름() {
        //given
        LocalDate now = LocalDate.now();
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        PassBook testPassBook = passBookMapper.selectAll(passBook.getUser(), pbKindsNum).get(0);

        //when
        boolean isDelete = deletePassBookService.deletePassBook(testPassBook.getNum(), "test1");

        //then
        assertThat(isDelete).isFalse();
    }

    @Test
    void 삭제_정상_비작동_NUM_다름() {
        //given
        LocalDate now = LocalDate.now();
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        boolean isDelete = deletePassBookService.deletePassBook(1, passBook.getUser());

        //then
        assertThat(isDelete).isFalse();
    }
}