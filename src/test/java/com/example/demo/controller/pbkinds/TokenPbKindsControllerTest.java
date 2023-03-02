package com.example.demo.controller.pbkinds;

import com.example.demo.interceptor.LoginInterceptor;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TokenPbKindsControllerTest {
    private final TokenPbKindsController tokenPbKindsController;
    private final LoginInterceptor loginInterceptor;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    public TokenPbKindsControllerTest(TokenPbKindsController tokenPbKindsController, LoginInterceptor loginInterceptor) {
        this.tokenPbKindsController = tokenPbKindsController;
        this.loginInterceptor = loginInterceptor;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(tokenPbKindsController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 토큰_정상출력() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test");


        //when
        MockHttpServletRequestBuilder builder = get("/pbkinds/token")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tokenList"))
                .andExpect(forwardedUrl("thymeleaf/ledger/pbkinds/pbkinds-token"));
    }

    @Test
    void 토큰페이지_세션_없음() throws Exception {
        //given

        //when
        MockHttpServletRequestBuilder builder = get("/pbkinds/token");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}