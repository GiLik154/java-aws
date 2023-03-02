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
class DeletePbKindsControllerTest {
    private final DeletePbKindsController deletePbKindsController;
    private final LoginInterceptor loginInterceptor;
    private final PassBookKindsMapper passBookKindsMapper;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    public DeletePbKindsControllerTest(DeletePbKindsController deletePbKindsController,
                                       LoginInterceptor loginInterceptor,
                                       PassBookKindsMapper passBookKindsMapper) {
        this.deletePbKindsController = deletePbKindsController;
        this.loginInterceptor = loginInterceptor;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(deletePbKindsController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void delete_기본페이지() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", pbKindsNum);

        //when
        MockHttpServletRequestBuilder builder = get("/pbkinds/delete")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/ledger/pbkinds/pbkinds-delete"));
    }

    @Test
    void delete_정상작동() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", pbKindsNum);

        //when
        MockHttpServletRequestBuilder builder = post("/pbkinds/delete/check")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isDelete", true))
                .andExpect(forwardedUrl("thymeleaf/ledger/pbkinds/pbkinds-delete-check"));

        Assertions.assertThat(passBookKindsMapper.selectAllByUserId("test")).isEmpty();
    }

    @Test
    void delete_세션없음() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");

        //when
        MockHttpServletRequestBuilder builder = post("/pbkinds/delete/check");

        //then
        mvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Assertions.assertThat(passBookKindsMapper.selectAllByUserId("test")).isNotEmpty();
    }

    @Test
    void delete_번호다름() throws Exception {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");

        session = new MockHttpSession();
        session.setAttribute("userId", "test");
        session.setAttribute("pbKindsNum", 0);

        //when
        MockHttpServletRequestBuilder builder = post("/pbkinds/delete/check")
                .session(session);

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isDelete", false))
                .andExpect(forwardedUrl("thymeleaf/ledger/pbkinds/pbkinds-delete-check"));

        Assertions.assertThat(passBookKindsMapper.selectAllByUserId("test")).isNotEmpty();
    }
}