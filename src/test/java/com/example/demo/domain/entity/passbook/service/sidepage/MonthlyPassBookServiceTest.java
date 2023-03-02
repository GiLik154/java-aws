package com.example.demo.domain.entity.passbook.service.sidepage;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.mapper.PassBookKindsMapper;
import com.example.demo.mapper.PassBookMapper;
import com.example.demo.domain.entity.passbook.service.sidepage.dto.MonthlyPassBookDto;
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
class MonthlyPassBookServiceTest {
    private final MonthlyPassBookService monthlyPassBookService;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    MonthlyPassBookServiceTest(MonthlyPassBookService monthlyPassBookService, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.monthlyPassBookService = monthlyPassBookService;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 월별_패스북_출력_정상작동() {
        //given
        LocalDate now = LocalDate.now();
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();
        PassBook passBook1 = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);
        PassBook passBook2 = new PassBook("test", "출금", 1000, "테스트 출금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);
        passBookMapper.add(passBook1);
        passBookMapper.add(passBook2);

        //when
        MonthlyPassBookDto testDto =  monthlyPassBookService.getMonthlyPassBookDto("test", now.getYear(), now.getMonthValue(), pbKindsNum);

        long depositForMonthly = testDto.getDepositForMonthly();
        long withdrawalForMonthly = testDto.getWithdrawalForMonthly();
        String maxDepositForMonth = testDto.getMaxDepositForMonth().get(0).getContent();
        String maxWithdrawalForMonth = testDto.getMaxWithdrawalForMonth().get(0).getContent();
        
        //then
        Assertions.assertThat(depositForMonthly).isEqualTo(10000);
        Assertions.assertThat(withdrawalForMonthly).isEqualTo(-1000);
        Assertions.assertThat(maxDepositForMonth).isEqualTo("테스트 입금");
        Assertions.assertThat(maxWithdrawalForMonth).isEqualTo("테스트 출금");
    }

    @Test
    void 월별_패스북_출력_정상작동_Null_체크() {
        //given
        LocalDate now = LocalDate.now();
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        //when
        MonthlyPassBookDto testDto =  monthlyPassBookService.getMonthlyPassBookDto("test", now.getYear(), now.getMonthValue(), pbKindsNum);

        long depositForMonthly = testDto.getDepositForMonthly();
        long withdrawalForMonthly = testDto.getWithdrawalForMonthly();
        String maxDepositForMonth = testDto.getMaxDepositForMonth().get(0).getContent();
        String maxWithdrawalForMonth = testDto.getMaxWithdrawalForMonth().get(0).getContent();

        //then
        Assertions.assertThat(depositForMonthly).isZero();
        Assertions.assertThat(withdrawalForMonthly).isZero();
        Assertions.assertThat(maxDepositForMonth).isEqualTo("없음");
        Assertions.assertThat(maxWithdrawalForMonth).isEqualTo("없음");
    }
}