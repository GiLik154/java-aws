package com.example.demo.controller.pbkinds;

import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.mapper.PassBookKindsMapper;
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
class AddPbKindsControllerTest {
    private final AddPbKindsController addPbKindsController;
    private final LoginInterceptor loginInterceptor;
    private final PassBookKindsMapper passBookKindsMapper;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    public AddPbKindsControllerTest(AddPbKindsController addPbKindsController,
                                    LoginInterceptor loginInterceptor,
                                    PassBookKindsMapper passBookKindsMapper) {
        this.addPbKindsController = addPbKindsController;
        this.loginInterceptor = loginInterceptor;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(addPbKindsController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void add_기본페이지() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        //when
        MockHttpServletRequestBuilder builder = get("/pbkinds/add")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/ledger/pbkinds/pbkinds-add"));
    }

    @Test
    void add_정상작동() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("userId", "test");

        //when
        MockHttpServletRequestBuilder builder = post("/pbkinds/add/check")
                .param("name", "test")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isAdd", true))
                .andExpect(forwardedUrl("thymeleaf/ledger/pbkinds/pbkinds-add-check"));

        Assertions.assertThat(passBookKindsMapper.selectAllByUserId("test")).isNotEmpty();
    }

    @Test
    void add_아이디_세션없음() throws Exception {
        //given

        //when
        MockHttpServletRequestBuilder builder = post("/pbkinds/add/check")
                .param("name", "test");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Assertions.assertThat(passBookKindsMapper.selectAllByUserId("test")).isEmpty();
    }
}