package com.example.demo.domain.entity.passbookkinds.service.join;

import com.example.demo.mapper.PassBookKindsMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JoinUserPassBookKindsServiceImplTest {
    private final JoinUserPassBookKindsService joinUserPassBookKindsService;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    public JoinUserPassBookKindsServiceImplTest(JoinUserPassBookKindsService joinUserPassBookKindsService, PassBookKindsMapper passBookKindsMapper) {
        this.joinUserPassBookKindsService = joinUserPassBookKindsService;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 유저_업데이트_정상작동() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        String token = passBookKindsMapper.selectAllByUserId("test").get(0).getToken();

        //when
        boolean isJoinUser = joinUserPassBookKindsService.join("test123", token);

        //then
        Assertions.assertThat(isJoinUser).isTrue();
        Assertions.assertThat(passBookKindsMapper.selectAllByToken(token).getUser()).isEqualTo("test-test123");
    }

    @Test
    void 유저_업데이트_아이디중복() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        String token = passBookKindsMapper.selectAllByUserId("test").get(0).getToken();

        //when
        boolean isJoinUser = joinUserPassBookKindsService.join("test", token);

        //then
        Assertions.assertThat(isJoinUser).isFalse();
        Assertions.assertThat(passBookKindsMapper.selectAllByToken(token).getUser()).isNotEqualTo("test-test");
    }

    @Test
    void 유저_업데이트_토큰불일치() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        String token = passBookKindsMapper.selectAllByUserId("test").get(0).getToken();

        //when
        boolean isJoinUser = joinUserPassBookKindsService.join("test", "1234-5678-1234");

        //then
        Assertions.assertThat(isJoinUser).isFalse();
        Assertions.assertThat(passBookKindsMapper.selectAllByToken(token).getUser()).isNotEqualTo("test-test");
    }
}