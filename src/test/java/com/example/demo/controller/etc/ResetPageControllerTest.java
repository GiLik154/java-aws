package com.example.demo.controller.etc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
class ResetPageControllerTest {
    private final ResetPageController resetPageController;
    protected MockHttpSession session;
    private MockMvc mvc;

    @Autowired
    public ResetPageControllerTest(ResetPageController resetPageController) {
        this.resetPageController = resetPageController;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(resetPageController)
                .build();
    }

    @Test
    void 세션_리셋_NotChoice() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("isNotChoice", true);

        //when
        MockHttpServletRequestBuilder builders = get("/reset/isNotChoice")
                .session(session);
        //then
        mvc.perform(builders)
                .andExpect(status().is3xxRedirection());

        Assertions.assertThat(session.getAttribute("isNotChoice")).isNull();
    }

    @Test
    void 세션_리셋_NotLogin() throws Exception {
        //given
        session = new MockHttpSession();
        session.setAttribute("isNotLogin", true);

        //when
        MockHttpServletRequestBuilder builders = get("/reset/isNotLogin")
                .session(session);
        //then
        mvc.perform(builders)
                .andExpect(status().is3xxRedirection());

        Assertions.assertThat(session.getAttribute("isNotLogin")).isNull();
    }
}