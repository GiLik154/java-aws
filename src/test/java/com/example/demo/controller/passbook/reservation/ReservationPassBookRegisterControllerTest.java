package com.example.demo.controller.passbook.reservation;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.domain.entity.passbook.service.reservation.ReservationPassBookRegisterService;
import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.mapper.PassBookKindsMapper;
import com.example.demo.mapper.PassBookMapper;
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
class ReservationPassBookRegisterControllerTest {
    private final ReservationPassBookRegisterController reservationPassBookRegisterController;
    private final ReservationPassBookRegisterService reservationPassBookRegisterService;
    private final LoginInterceptor loginInterceptor;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    ReservationPassBookRegisterControllerTest(ReservationPassBookRegisterController reservationPassBookRegisterController, ReservationPassBookRegisterService reservationPassBookRegisterService, LoginInterceptor loginInterceptor, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.reservationPassBookRegisterController = reservationPassBookRegisterController;
        this.reservationPassBookRegisterService = reservationPassBookRegisterService;
        this.loginInterceptor = loginInterceptor;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(reservationPassBookRegisterController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 예약목록_개별목록_출력() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        LocalDate now = LocalDate.now();
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);

        session.setAttribute("pbKindsNum", pbKindsNum);

        passBookMapper.add(passBook);
        int num = passBookMapper.selectAll("test", pbKindsNum).get(0).getNum();

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/check")
                .session(session)
                .param("num", String.valueOf(num));

        //then

        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("reservationPassBookEach", reservationPassBookRegisterService.selectEach(num, "test", pbKindsNum)))
                .andExpect(forwardedUrl("thymeleaf/ledger/passbook/reservation/calendar-reservation-check"));
    }

    @Test
    void 예약목록_개별목록_오류_세션없음() throws Exception {
        //given
        LocalDate now = LocalDate.now();

        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        int num = passBookMapper.selectAll("test", pbKindsNum).get(0).getNum();

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/check")
                .param("num", String.valueOf(num));

        //then

        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void 예약목록_등록() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        LocalDate now = LocalDate.now();

        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "예약", pbKindsNum);

        session.setAttribute("pbKindsNum", pbKindsNum);

        passBookMapper.add(passBook);

        int num = passBookMapper.selectAll("test", pbKindsNum).get(0).getNum();

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/register")
                .session(session)
                .param("num", String.valueOf(num));

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isRegister", true))
                .andExpect(forwardedUrl("thymeleaf/ledger/passbook/reservation/calendar-reservation-register"));

    }

    @Test
    void 예약목록_등록_오류_세션없음() throws Exception {
        //given
        LocalDate now = LocalDate.now();

        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/register");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}