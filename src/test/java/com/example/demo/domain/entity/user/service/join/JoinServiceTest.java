package com.example.demo.domain.entity.user.service.join;

import com.example.demo.domain.entity.user.domain.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.domain.entity.user.service.dto.JoinServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JoinServiceTest {
    private final JoinService joinService;
    private final UserMapper userMapper;

    @Autowired
    JoinServiceTest(JoinService joinService, UserMapper userMapper) {
        this.joinService = joinService;
        this.userMapper = userMapper;
    }

    @Test
    void 회원가입_정상() {
        //given
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test123", "test");

        //when
        boolean result = joinService.add(joinServiceDto);
        User user = userMapper.getInfo("test");

        //then
        assertTrue(result);
        assertEquals("test", user.getId());
        assertNotEquals("test", user.getPw());
        assertEquals("test", user.getName());
    }

    @Test
    void 회원가입_아이디_중복() {
        //given
        JoinServiceDto joinServiceDto = new JoinServiceDto("root", "test123", "test");
        joinService.add(joinServiceDto);

        //when
        boolean result = joinService.add(joinServiceDto);

        //then
        assertFalse(result);
    }
}