package com.example.demo.domain.entity.passbook.service.sidepage;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.mapper.PassBookKindsMapper;
import com.example.demo.mapper.PassBookMapper;
import com.example.demo.domain.entity.passbook.service.sidepage.dto.YearlyPassBookDto;
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
class YearlyPassBookServiceTest {
    private final YearlyPassBookService yearlyPassBookService;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    YearlyPassBookServiceTest(YearlyPassBookService yearlyPassBookService, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.yearlyPassBookService = yearlyPassBookService;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 년별_패스북_출력_정상작동() {
        //given
        LocalDate now = LocalDate.now();
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();
        PassBook passBook1 = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);
        PassBook passBook2 = new PassBook("test", "출금", 1000, "테스트 출금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);
        passBookMapper.add(passBook1);
        passBookMapper.add(passBook2);

        //when
        YearlyPassBookDto testDto = yearlyPassBookService.getYearlyPassBookDto("test", now.getYear(), pbKindsNum);

        long depositForYearly = testDto.getDepositForYearly();
        long withdrawalForYearly = testDto.getWithdrawalForYearly();
        String maxDepositForYear = testDto.getMaxDepositForYear().get(0).getContent();
        String maxWithdrawalForYear = testDto.getMaxWithdrawalForYear().get(0).getContent();

        //then
        Assertions.assertThat(depositForYearly).isEqualTo(10000);
        Assertions.assertThat(withdrawalForYearly).isEqualTo(-1000);
        Assertions.assertThat(maxDepositForYear).isEqualTo("테스트 입금");
        Assertions.assertThat(maxWithdrawalForYear).isEqualTo("테스트 출금");
    }

    @Test
    void 월별_패스북_출력_정상작동_Null_체크() {
        //given
        LocalDate now = LocalDate.now();
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        //when
        YearlyPassBookDto testDto = yearlyPassBookService.getYearlyPassBookDto("test", now.getYear(), pbKindsNum);

        long depositForYearly = testDto.getDepositForYearly();
        long withdrawalForYearly = testDto.getWithdrawalForYearly();
        String maxDepositForYear = testDto.getMaxDepositForYear().get(0).getContent();
        String maxWithdrawalForYear = testDto.getMaxWithdrawalForYear().get(0).getContent();

        //then
        Assertions.assertThat(depositForYearly).isZero();
        Assertions.assertThat(withdrawalForYearly).isZero();
        Assertions.assertThat(maxDepositForYear).isEqualTo("없음");
        Assertions.assertThat(maxWithdrawalForYear).isEqualTo("없음");
    }
}