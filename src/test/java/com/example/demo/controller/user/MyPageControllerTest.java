package com.example.demo.controller.user;

import com.example.demo.domain.entity.user.domain.User;
import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
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
class MyPageControllerTest {
    private final MyPageController myPageController;
    private final LoginInterceptor loginInterceptor;
    private final UserMapper userMapper;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    MyPageControllerTest(MyPageController myPageController, LoginInterceptor loginInterceptor, UserMapper userMapper) {
        this.myPageController = myPageController;
        this.loginInterceptor = loginInterceptor;
        this.userMapper = userMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(myPageController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 마이페이지_정보_출력() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        User user = new User("test", "test123", "test");
        userMapper.add(user);

        //when
        MockHttpServletRequestBuilder builder = get("/my-page")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userInfo"))
                .andExpect(forwardedUrl("thymeleaf/user/my-page"));
    }

    @Test
    void 마이페이지_정보_출력_오류_세션없음() throws Exception {
        //given
        User user = new User("test", "test123", "test");
        userMapper.add(user);

        //when
        MockHttpServletRequestBuilder builder = get("/my-page");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void 수정_사이트_정보출력() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        User user = new User("test", "test123", "test");
        userMapper.add(user);

        //when
        MockHttpServletRequestBuilder builder = get("/my-page/info-revise")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userInfo"))
                .andExpect(forwardedUrl("thymeleaf/user/info-revise"));
    }

    @Test
    void 수정_사이트_정보출력_오류_세션없음() throws Exception {
        //given
        User user = new User("test", "test123", "test");
        userMapper.add(user);

        //when
        MockHttpServletRequestBuilder builder = get("/my-page/info-revise");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void 정보_수정() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        User user = new User("test", "test123", "test");
        userMapper.add(user);

        //when
        MockHttpServletRequestBuilder builder = post("/my-page/revise-check")
                .session(session)
                .param("name", "test")
                .param("age", "19")
                .param("sex", "man")
                .param("phone", "000-0000-0000")
                .param("hobby", "fishing");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isRevise", true))
                .andExpect(forwardedUrl("thymeleaf/user/revise-check"));

    }

    @Test
    void 정보_수정_오류_아이디다름() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        User user = new User("test123", "test123", "test");
        userMapper.add(user);

        //when
        MockHttpServletRequestBuilder builder = post("/my-page/revise-check")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isRevise", false))
                .andExpect(forwardedUrl("thymeleaf/user/revise-check"));
    }

    @Test
    void 정보_수정_오류_세션없음() throws Exception {
        //given
        User user = new User("test", "test123", "test");
        userMapper.add(user);

        //when
        MockHttpServletRequestBuilder builder = post("/my-page/revise-check");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}