package com.example.demo.domain.entity.passbookkinds.service.cancel;

import com.example.demo.mapper.PassBookKindsMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CancellingPassBookKindsServiceImplTest {
    private final CancellingPassBookKindsService cancellingPassBookKindsService;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    public CancellingPassBookKindsServiceImplTest(CancellingPassBookKindsService cancellingPassBookKindsService, PassBookKindsMapper passBookKindsMapper) {
        this.cancellingPassBookKindsService = cancellingPassBookKindsService;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 가계부_탈퇴_정상처리() {
        //given
        passBookKindsMapper.add("test", "test-test123", "0000-0000-0000");
        int pbKindsNum = passBookKindsMapper.selectAllByToken("0000-0000-0000").getNum();

        //when
        boolean isCancelling = cancellingPassBookKindsService.cancelling("test123", pbKindsNum);

        //then
        assertTrue(isCancelling);
        Assertions.assertThat(passBookKindsMapper.selectAllByPbKindsNum(pbKindsNum).getUser()).isEqualTo("test");
    }

    @Test
    void 가계부_탈퇴_아이디없음() {
        //given
        passBookKindsMapper.add("test", "test-test123", "0000-0000-0000");
        int pbKindsNum = passBookKindsMapper.selectAllByToken("0000-0000-0000").getNum();

        //when
        boolean isCancelling = cancellingPassBookKindsService.cancelling("test888", pbKindsNum);

        //then
        assertFalse(isCancelling);
        Assertions.assertThat(passBookKindsMapper.selectAllByPbKindsNum(pbKindsNum).getUser()).isEqualTo("test-test123");
    }

    @Test
    void 가계부_탈퇴_번호불일치() {
        //given
        passBookKindsMapper.add("test", "test-test123", "0000-0000-0000");
        int pbKindsNum = passBookKindsMapper.selectAllByToken("0000-0000-0000").getNum();

        //when
        boolean isCancelling = cancellingPassBookKindsService.cancelling("test888", 1);

        //then
        assertFalse(isCancelling);
        Assertions.assertThat(passBookKindsMapper.selectAllByPbKindsNum(pbKindsNum).getUser()).isEqualTo("test-test123");
    }
}