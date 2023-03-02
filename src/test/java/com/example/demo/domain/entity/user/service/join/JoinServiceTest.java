package com.example.demo.domain.entity.user.service.join;

import com.example.demo.mapper.UserMapper;
import com.example.demo.domain.entity.user.service.dto.JoinServiceDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JoinServiceTest {
    private final JoinService joinService;
    private final UserMapper userMapper;

    @Autowired
    JoinServiceTest(JoinService joinService, UserMapper userMapper) {
        this.joinService = joinService;
        this.userMapper = userMapper;
    }

    @Test
    void 회원가입_정상() {
        //given
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test123", "test");

        //when
        boolean result = joinService.add(joinServiceDto);

        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void 아이디_중복() {
        //given
        JoinServiceDto joinServiceDto = new JoinServiceDto("root", "test123", "test");
        joinService.add(joinServiceDto);

        //when
        boolean result = joinService.add(joinServiceDto);

        //then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void 비밀번호_암호화() {
        //given
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test123", "test");

        //when
        joinService.add(joinServiceDto);
        String testPw = userMapper.getPw("test");

        //then
        Assertions.assertThat(testPw).isNotEqualTo("test123");
    }
}