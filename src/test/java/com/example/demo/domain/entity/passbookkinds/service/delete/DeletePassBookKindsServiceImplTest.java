package com.example.demo.domain.entity.passbookkinds.service.delete;

import com.example.demo.mapper.PassBookKindsMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeletePassBookKindsServiceImplTest {
    private final DeletePassBookKindsService deletePassBookKindsService;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    public DeletePassBookKindsServiceImplTest(DeletePassBookKindsService deletePassBookKindsService, PassBookKindsMapper passBookKindsMapper) {
        this.deletePassBookKindsService = deletePassBookKindsService;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 삭제_정상작동() {
        //given
        passBookKindsMapper.add("test", "test", "test");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        //when
        boolean isDelete = deletePassBookKindsService.delete("test", pbKindsNum);

        //then
        assertTrue(isDelete);
        Assertions.assertThat(passBookKindsMapper.selectAllByUserId("test")).isEmpty();
    }

    @Test
    void 삭제_생성자_아님() {
        //given
        passBookKindsMapper.add("test", "test-test123", "test");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        //when
        boolean isDelete = deletePassBookKindsService.delete("test123", pbKindsNum);

        //then
        assertFalse(isDelete);
        Assertions.assertThat(passBookKindsMapper.selectAllByUserId("test")).isNotEmpty();
    }

    @Test
    void 삭제_번호_다름() {
        //given
        passBookKindsMapper.add("test", "test", "test");

        //when
        boolean isDelete = deletePassBookKindsService.delete("test", 0);

        //then
        assertFalse(isDelete);
        Assertions.assertThat(passBookKindsMapper.selectAllByUserId("test")).isNotEmpty();
    }
}