package com.example.demo.domain.entity.user.service.login;

import com.example.demo.domain.entity.user.domain.User;
import com.example.demo.domain.entity.user.service.dto.JoinServiceDto;
import com.example.demo.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoginServiceTest {
    private final LoginService loginService;
    private final UserMapper userMapper;
    private final PasswordEncoder byPasswordEncoder;

    @Autowired
    public LoginServiceTest(LoginService loginService, UserMapper userMapper, PasswordEncoder byPasswordEncoder) {
        this.loginService = loginService;
        this.userMapper = userMapper;
        this.byPasswordEncoder = byPasswordEncoder;
    }
    @Test
    void 로그인_정상() {
        //given
        User user = new User("test", byPasswordEncoder.encode("test123"), "test");
        userMapper.add(user);

        //when
        boolean isLogin = loginService.isCompare("test", "test123");
        //then
        Assertions.assertThat(isLogin).isTrue();
    }

    @Test
    void 아이디_오류() {
        //given
        User user = new User("test", byPasswordEncoder.encode("test123"), "test");
        userMapper.add(user);

        //when
        boolean isLogin = loginService.isCompare("test1", "test123");

        //then
        Assertions.assertThat(isLogin).isFalse();
    }

    @Test
    void 비밀번호_오류() {
        //given
        User user = new User("test", byPasswordEncoder.encode("test123"), "test");
        userMapper.add(user);

        //when
        boolean isLogin = loginService.isCompare("test", "test1234");

        //then
        Assertions.assertThat(isLogin).isFalse();
    }
}