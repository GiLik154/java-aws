package com.example.demo.controller.passbook.reservation;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.domain.entity.passbook.service.reservation.ReservationPassBookMainService;
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
class ReservationPassBookDeleteControllerTest {
    private final ReservationPassBookDeleteController reservationPassBookDeleteController;
    private final ReservationPassBookMainService reservationPassBookMainService;
    private final LoginInterceptor loginInterceptor;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    ReservationPassBookDeleteControllerTest(ReservationPassBookDeleteController reservationPassBookDeleteController, ReservationPassBookMainService reservationPassBookMainService, LoginInterceptor loginInterceptor, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.reservationPassBookDeleteController = reservationPassBookDeleteController;
        this.reservationPassBookMainService = reservationPassBookMainService;
        this.loginInterceptor = loginInterceptor;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(reservationPassBookDeleteController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 리스트_정상_출력() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", pbKindsNum);

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/delete")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("reservationPassBookListAll", reservationPassBookMainService.selectAll("test", pbKindsNum)))
                .andExpect(forwardedUrl("thymeleaf/ledger/passbook/reservation/calendar-reservation-delete"));

    }

    @Test
    void 리스트_정상_세션_없음() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/delete");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void 삭제_사이트_정상작동() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", pbKindsNum);

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        String num = String.valueOf(passBookMapper.selectAll("test", pbKindsNum).get(0).getNum());

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/delete/check")
                .session(session)
                .param("num", num);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isDelete", true))
                .andExpect(forwardedUrl("thymeleaf/ledger/passbook/reservation/calendar-reservation-delete-check"));

        Assertions.assertThat(passBookMapper.selectAllCount("test", pbKindsNum)).isZero();

    }

    @Test
    void 삭제_사이트_세션_없음() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        String num = String.valueOf(passBookMapper.selectAll("test", pbKindsNum).get(0).getNum());

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/delete/check")
                .param("num", num);

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Assertions.assertThat(passBookMapper.selectAllCount("test", pbKindsNum)).isNotZero();

    }

    @Test
    void 삭제_사이트_넘버_다름() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", pbKindsNum);

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/delete/check")
                .session(session)
                .param("num", "1");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isDelete", false))
                .andExpect(forwardedUrl("thymeleaf/ledger/passbook/reservation/calendar-reservation-delete-check"));

        Assertions.assertThat(passBookMapper.selectAllCount("test", pbKindsNum)).isNotZero();

    }
}