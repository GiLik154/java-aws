package com.example.demo.controller.user;

import com.example.demo.domain.entity.user.domain.User;
import com.example.demo.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
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
class JoinControllerTest {
    private final JoinController joinController;
    private final UserMapper userMapper;
    private MockMvc mvc;

    @Autowired
    public JoinControllerTest(JoinController joinController, UserMapper userMapper) {
        this.joinController = joinController;
        this.userMapper = userMapper;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(joinController).build();
    }

    @Test
    void 회원가입_기본사이트() throws Exception {
        //given
        //when
        MockHttpServletRequestBuilder builder = get("/join");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/join"));
    }

    @Test
    void 회원가입_정상작동() throws Exception {
        //given
        //when
        MockHttpServletRequestBuilder builder = post("/join/check")
                .param("id", "test123")
                .param("pw", "test123")
                .param("name", "test");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isJoined", true))
                .andExpect(forwardedUrl("thymeleaf/user/join-check"));

        Assertions.assertThat(userMapper.selectUser("test123")).isEqualTo(1);
    }

    @Test
    void 회원가입_오류_아이디중복() throws Exception {
        //given
        User user = new User("test123", "test123", "test");
        userMapper.add(user);
        //when
        MockHttpServletRequestBuilder builder = post("/join/check")
                .param("id", "test123")
                .param("pw", "test123")
                .param("name", "test");

        //then
        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("isJoined", false))
                .andExpect(forwardedUrl("thymeleaf/user/join-check"));
    }
}