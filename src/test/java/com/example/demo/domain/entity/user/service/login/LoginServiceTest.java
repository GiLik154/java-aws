package com.example.demo.domain.entity.user.service.login;

import com.example.demo.domain.entity.user.domain.User;
import com.example.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(isLogin);
    }

    @Test
    void 로그인_아이디_오류() {
        //given
        User user = new User("test", byPasswordEncoder.encode("test123"), "test");
        userMapper.add(user);

        //when
        boolean isLogin = loginService.isCompare("test1", "test123");

        //then
        assertFalse(isLogin);
    }

    @Test
    void 로그인_비밀번호_오류() {
        //given
        User user = new User("test", byPasswordEncoder.encode("test123"), "test");
        userMapper.add(user);

        //when
        boolean isLogin = loginService.isCompare("test", "test1234");

        //then
        assertFalse(isLogin);
    }
}