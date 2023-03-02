package com.example.demo.controller.etc;

import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.mapper.PassBookKindsMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
class SelectKindsControllerTest {
    private final SelectKindsController selectKindsController;
    private final LoginInterceptor loginInterceptor;
    private final PassBookKindsMapper passBookKindsMapper;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    SelectKindsControllerTest(SelectKindsController selectKindsController, LoginInterceptor loginInterceptor, PassBookKindsMapper passBookKindsMapper) {
        this.selectKindsController = selectKindsController;
        this.loginInterceptor = loginInterceptor;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(selectKindsController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 초이스_정상작동() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        //when
        MockHttpServletRequestBuilder builder = get("/choice")
                .session(session)
                .param("choiceKindsNum", String.valueOf(pbKindsNum));

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/ledger/main_page/calendar-choice"));

        Assertions.assertThat(session.getAttribute("pbKindsNum")).isEqualTo(pbKindsNum);
    }

    @Test
    void 초이스_정상작동_선택안함() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");

        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        //when
        MockHttpServletRequestBuilder builder = get("/choice")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/ledger/main_page/calendar-choice"));

        Assertions.assertThat(session.getAttribute("pbKindsNum")).isEqualTo(0);
    }

    @Test
    void 초이스_정상작동_세션없음() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        //when
        MockHttpServletRequestBuilder builder = get("/choice")
                .param("choiceKindsNum", String.valueOf(pbKindsNum));

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
        MockHttpServletRequestBuilder builder = get("/choice/exit")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/login"));

        assertThat(session.getAttribute("pbKindsNum")).isNull();
    }
}