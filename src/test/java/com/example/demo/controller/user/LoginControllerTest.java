package com.example.demo.controller.user;

import com.example.demo.domain.entity.user.service.join.JoinService;
import com.example.demo.domain.entity.user.service.dto.JoinServiceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoginControllerTest {
    private final LoginController loginController;
    private final JoinService joinService;
    private MockMvc mvc;

    @Autowired
    public LoginControllerTest(LoginController loginController, JoinService joinService) {
        this.loginController = loginController;
        this.joinService = joinService;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void 로그인_기본페이지() throws Exception {
        //given
        //when
        MockHttpServletRequestBuilder builder = get("/login");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/login"));
    }

    @Test
    void 로그인_체크() throws Exception {
        //given
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test123", "test");
        joinService.add(joinServiceDto);

        //when
        MockHttpServletRequestBuilder builder = post("/login/check")
                .param("id", "test")
                .param("pw", "test123");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isLogin", true))
                .andExpect(model().attribute("userId", "test"))
                .andExpect(forwardedUrl("thymeleaf/user/login-check"));
    }

    @Test
    void 로그인_실패_아이디틀림() throws Exception {
        //given
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test123", "test");
        joinService.add(joinServiceDto);
        //when
        MockHttpServletRequestBuilder builder = post("/login/check")
                .param("id", "test2")
                .param("pw", "test123");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isLogin", false))
                .andExpect(model().attributeDoesNotExist("userId"))
                .andExpect(forwardedUrl("thymeleaf/user/login-check"));
    }
    
    @Test
    void 로그인_실패_비밀번호틀림() throws Exception {
        //given
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test123", "test");
        joinService.add(joinServiceDto);
                //when
                MockHttpServletRequestBuilder builder = post("/login/check")
                .param("id", "test")
                .param("pw", "test");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isLogin", false))
                .andExpect(model().attributeDoesNotExist("userId"))
                .andExpect(forwardedUrl("thymeleaf/user/login-check"));
    }
}