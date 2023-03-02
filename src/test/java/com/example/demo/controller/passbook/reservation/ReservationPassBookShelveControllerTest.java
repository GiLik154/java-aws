package com.example.demo.controller.passbook.reservation;

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
class ReservationPassBookShelveControllerTest {
    private final ReservationPassBookShelveController reservationPassBookShelveController;
    private final LoginInterceptor loginInterceptor;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;

    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    ReservationPassBookShelveControllerTest(ReservationPassBookShelveController reservationPassBookShelveController, LoginInterceptor loginInterceptor, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.reservationPassBookShelveController = reservationPassBookShelveController;
        this.loginInterceptor = loginInterceptor;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(reservationPassBookShelveController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 다음달_예약_생성() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);

        passBookMapper.add(passBook);

        int num = passBookMapper.selectReservationAll("test", pbKindsNum).get(0).getNum();

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/shelve")
                .session(session)
                .param("num", String.valueOf(num));

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isShelve", true))
                .andExpect(forwardedUrl("thymeleaf/ledger/passbook/reservation/calendar-reservation-shelved"));
        Assertions.assertThat(passBookMapper.selectAll("test", pbKindsNum).get(0).getMonth()).isEqualTo(now.plusMonths(1).getMonthValue());
    }

    @Test
    void 다음달_예약_생성_오류_세션없음() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/shelve");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}