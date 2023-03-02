package com.example.demo.controller.mainpage;

import com.example.demo.interceptor.CheckPassBookKindsInterceptor;
import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.mapper.PassBookKindsMapper;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CalendarMainPageControllerTest {
    private final CalendarMainPageController calendarMainPageController;
    private final LoginInterceptor loginInterceptor;
    private final CheckPassBookKindsInterceptor checkPassBookKindsInterceptor;
    private final PassBookKindsMapper passBookKindsMapper;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    CalendarMainPageControllerTest(CalendarMainPageController calendarMainPageController, LoginInterceptor loginInterceptor, CheckPassBookKindsInterceptor checkPassBookKindsInterceptor, PassBookKindsMapper passBookKindsMapper) {
        this.calendarMainPageController = calendarMainPageController;
        this.loginInterceptor = loginInterceptor;
        this.checkPassBookKindsInterceptor = checkPassBookKindsInterceptor;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(calendarMainPageController)
                .addInterceptors(loginInterceptor)
                .addInterceptors(checkPassBookKindsInterceptor)
                .build();
    }

    @Test
    void 메인페이지_실행() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", pbKindsNum);


        //when
        MockHttpServletRequestBuilder builder = get("/main")
                .session(session)
                .param("date", "");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/ledger/main_page/calendar-main"));

    }

    @Test
    void 메인페이지_넘버세션없음() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        //when
        MockHttpServletRequestBuilder builder = get("/main")
                .session(session)
                .param("date", "");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/choice"));

    }

    @Test
    void 메인페이지_실행_오류_세션없음() throws Exception {
        //given
        //when
        MockHttpServletRequestBuilder builder = get("/main");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void 세선_삭제_테스트() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", pbKindsNum);

        //when
        MockHttpServletRequestBuilder builder = get("/exit")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/login"));

        assertThat(session.getAttribute("pbKindsNum")).isNull();
    }
}