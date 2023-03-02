package com.example.demo.controller.passbook;

import com.example.demo.controller.passbook.delete.DeletePassBookController;
import com.example.demo.domain.entity.passbook.domain.PassBook;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeletePassBookControllerTest {
    private final DeletePassBookController deletePassBookController;
    private final LoginInterceptor loginInterceptor;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    DeletePassBookControllerTest(DeletePassBookController deletePassBookController, LoginInterceptor loginInterceptor, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.deletePassBookController = deletePassBookController;
        this.loginInterceptor = loginInterceptor;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(deletePassBookController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 딜리트_기본_페이지() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", pbKindsNum);

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/details/delete")
                .session(session)
                .param("date", "");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/ledger/passbook/calendar-delete"));
    }

    @Test
    void 딜리트_체크_페이지() throws Exception {
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", pbKindsNum);

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        int num = passBookMapper.selectAll("test", pbKindsNum).get(0).getNum();

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/details/delete-check")
                .session(session)
                .param("date", "")
                .param("num", String.valueOf(num));

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isDelete", true))
                .andExpect(forwardedUrl("thymeleaf/ledger/passbook/calendar-delete-check"));

        Assertions.assertThat(passBookMapper.selectAllCount("test", pbKindsNum)).isZero();

    }

    @Test
    void 딜리트_체크_페이지_작성자불일치() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test123");
        session.setAttribute("pbKindsNum", pbKindsNum);

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        int num = passBookMapper.selectAll("test", pbKindsNum).get(0).getNum();

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/details/delete-check")
                .session(session)
                .param("date", "")
                .param("num", String.valueOf(num));

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isDelete", false))
                .andExpect(forwardedUrl("thymeleaf/ledger/passbook/calendar-delete-check"));

        Assertions.assertThat(passBookMapper.selectAllCount("test", pbKindsNum)).isNotZero();

    }

    @Test
    void 딜리트_체크_페이지_세션없음() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", pbKindsNum);

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        int num = passBookMapper.selectAll("test", pbKindsNum).get(0).getNum();

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/details/delete-check")
                .param("date", "")
                .param("num", String.valueOf(num));

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Assertions.assertThat(passBookMapper.selectAllCount("test", pbKindsNum)).isNotZero();

    }
}