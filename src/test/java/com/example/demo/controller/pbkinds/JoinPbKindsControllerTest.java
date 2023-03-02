package com.example.demo.controller.pbkinds;

import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.mapper.PassBookKindsMapper;
import com.example.demo.domain.entity.passbookkinds.service.add.AddPassBookKindsService;
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
class JoinPbKindsControllerTest {
    private final JoinPbKindsController joinPbKindsController;
    private final AddPassBookKindsService addPassBookKindsService;
    private final LoginInterceptor loginInterceptor;
    private final PassBookKindsMapper passBookKindsMapper;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    JoinPbKindsControllerTest(JoinPbKindsController joinPbKindsController, AddPassBookKindsService addPassBookKindsService, LoginInterceptor loginInterceptor, PassBookKindsMapper passBookKindsMapper) {
        this.joinPbKindsController = joinPbKindsController;
        this.addPassBookKindsService = addPassBookKindsService;
        this.loginInterceptor = loginInterceptor;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(joinPbKindsController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void join_기본페이지() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test123");

        //when
        MockHttpServletRequestBuilder builder = get("/pbkinds/join")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/ledger/pbkinds/pbkinds-join"));
    }

    @Test
    void join_정상작동() throws Exception {
        //given
        addPassBookKindsService.add("test", "test");
        String token = passBookKindsMapper.selectAllByUserId("test").get(0).getToken();

        session = new MockHttpSession();
        session.setAttribute("userId", "test123");

        //when
        MockHttpServletRequestBuilder builder = post("/pbkinds/join/check")
                .session(session)
                .param("token", token);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isJoin", true))
                .andExpect(forwardedUrl("thymeleaf/ledger/pbkinds/pbkinds-join-check"));

        Assertions.assertThat(passBookKindsMapper.selectAllByToken(token).getUser()).isEqualTo("test-test123");
    }

    @Test
    void join_아이디_중복() throws Exception {
        //given
        addPassBookKindsService.add("test123", "test");
        String token = passBookKindsMapper.selectAllByUserId("test").get(0).getToken();

        session = new MockHttpSession();
        session.setAttribute("userId", "test123");

        //when
        MockHttpServletRequestBuilder builder = post("/pbkinds/join/check")
                .session(session)
                .param("token", token);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isJoin", false))
                .andExpect(forwardedUrl("thymeleaf/ledger/pbkinds/pbkinds-join-check"));

        Assertions.assertThat(passBookKindsMapper.selectAllByToken(token).getUser()).isEqualTo("test");
    }

    @Test
    void join_토큰다름_중복() throws Exception {
        //given
        addPassBookKindsService.add("test123", "test");
        String token = passBookKindsMapper.selectAllByUserId("test").get(0).getToken();

        session = new MockHttpSession();
        session.setAttribute("userId", "test123");

        //when
        MockHttpServletRequestBuilder builder = post("/pbkinds/join/check")
                .session(session)
                .param("token", "7777-7777-7777");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isJoin", false))
                .andExpect(forwardedUrl("thymeleaf/ledger/pbkinds/pbkinds-join-check"));

        Assertions.assertThat(passBookKindsMapper.selectAllByToken(token).getUser()).isEqualTo("test");
    }

    @Test
    void join_세션없음() throws Exception {
        //given
        addPassBookKindsService.add("test", "test");
        String token = passBookKindsMapper.selectAllByUserId("test").get(0).getToken();

        //when
        MockHttpServletRequestBuilder builder = post("/pbkinds/join/check")
                .param("token", "7777-7777-7777");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Assertions.assertThat(passBookKindsMapper.selectAllByToken(token).getUser()).isEqualTo("test");
    }
}