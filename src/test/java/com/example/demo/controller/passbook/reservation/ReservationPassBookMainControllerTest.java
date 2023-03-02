package com.example.demo.controller.passbook.reservation;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.domain.entity.passbook.service.reservation.ReservationPassBookMainService;
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
class ReservationPassBookMainControllerTest {
    private final ReservationPassBookMainController reservationPassBookMainController;
    private final ReservationPassBookMainService reservationPassBookMainService;
    private final LoginInterceptor loginInterceptor;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    ReservationPassBookMainControllerTest(ReservationPassBookMainController reservationPassBookMainController, ReservationPassBookMainService reservationPassBookMainService, LoginInterceptor loginInterceptor, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.reservationPassBookMainController = reservationPassBookMainController;
        this.reservationPassBookMainService = reservationPassBookMainService;
        this.loginInterceptor = loginInterceptor;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(reservationPassBookMainController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 예약목록_출력() throws Exception {
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
        MockHttpServletRequestBuilder builder = get("/calendar/reservation")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("reservationPassBookList", reservationPassBookMainService.selectList("test", pbKindsNum)))
                .andExpect(forwardedUrl("thymeleaf/ledger/passbook/reservation/calendar-reservation"));
    }

    @Test
    void 예약목록_출력_오류_세션없음() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void 예약목록_전체출력() throws Exception {
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
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/all")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("reservationPassBookListAll", reservationPassBookMainService.selectAll("test", pbKindsNum)))
                .andExpect(forwardedUrl("thymeleaf/ledger/passbook/reservation/calendar-reservation-all"));
    }

    @Test
    void 예약목록_전체출력_오류_세션없음() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        LocalDate now = LocalDate.now();
        PassBook passBook = new PassBook("test", "입금", 10000, "테스트 입금", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), "일반", pbKindsNum);

        passBookMapper.add(passBook);

        //when
        MockHttpServletRequestBuilder builder = get("/calendar/reservation/all");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}