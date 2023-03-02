package com.example.demo.controller.passbook;

import com.example.demo.controller.passbook.add.AddPassBookController;
import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.mapper.PassBookKindsMapper;
import com.example.demo.mapper.PassBookMapper;
import org.assertj.core.api.Assertions;
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
class AddPassBookControllerTest {
    private final AddPassBookController addPassBookController;
    private final LoginInterceptor loginInterceptor;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    AddPassBookControllerTest(AddPassBookController addPassBookController, LoginInterceptor loginInterceptor, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.addPassBookController = addPassBookController;
        this.loginInterceptor = loginInterceptor;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(addPassBookController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void add_기본홈페이지() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/details/add")
                .session(session);

        //then
        mvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    void add_체크_홈페이지_정상작동() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", pbKindsNum);

        //when
        MockHttpServletRequestBuilder builder = post("/calendar/details/add-check").session(session).param("date", "").param("kinds", "입금").param("price", "1000").param("content", "테스트중").param("reservation", "일반");

        //then
        mvc.perform(builder).andExpect(status().isOk()).andExpect(model().attribute("isAdd", true)).andExpect(forwardedUrl("thymeleaf/ledger/passbook/calendar-add-check"));

        Assertions.assertThat(passBookMapper.selectAllCount("test", pbKindsNum)).isEqualTo(1);
    }

    @Test
    void add_체크_홈페이지_정상작동_세션없음() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        //when
        MockHttpServletRequestBuilder builder = post("/calendar/details/add-check").param("date", "").param("kinds", "입금").param("price", "1000").param("content", "테스트중").param("reservation", "일반");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Assertions.assertThat(passBookMapper.selectAllCount("test", pbKindsNum)).isZero();

    }
}