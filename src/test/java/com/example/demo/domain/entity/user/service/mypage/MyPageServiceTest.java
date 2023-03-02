package com.example.demo.domain.entity.user.service.mypage;

import com.example.demo.domain.entity.user.domain.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.domain.entity.user.service.dto.ReviseServiceDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MyPageServiceTest {
    private final MyPageService myPageService;
    private final UserMapper userMapper;

    @Autowired
    MyPageServiceTest(MyPageService myPageService, UserMapper userMapper) {
        this.myPageService = myPageService;
        this.userMapper = userMapper;
    }
    @Test
    void 정보_가져오기() {
        //given
        User user1 = new User("test", "test123", "test");
        User user2 = new User("test", "이름", 19, "man", "01000000000", "fishing");
        userMapper.add(user1);
        userMapper.reviseInfo(user2);

        //when
        User testUser = myPageService.getInfo("test");

        //then
        Assertions.assertThat(testUser.getId()).isEqualTo("test");
        Assertions.assertThat(testUser.getSex()).isEqualTo("man");
    }

    @Test
    void 정보_가져오기_아이디오류() {
        //given
        User user1 = new User("test", "test123", "test");
        User user2 = new User("test", "이름", 19, "man", "01000000000", "fishing");
        userMapper.add(user1);
        userMapper.reviseInfo(user2);

        //when
        User testUser = myPageService.getInfo("test1");

        //then
        Assertions.assertThat(testUser).isNull();
    }

    @Test
    void 정보_가져오기_정보오류() {
        //given
        User user1 = new User("test", "test123", "test");
        User user2 = new User("test", "이름", 19, "man", "01000000000", "fishing");
        userMapper.add(user1);
        userMapper.reviseInfo(user2);

        //when
        User testUser = myPageService.getInfo("test");

        //then
        Assertions.assertThat(testUser.getSex()).isNotEqualTo("woman");
    }

    @Test
    void 정보수정_정상() {
        //given
        User user1 = new User("test", "test123", "test");
        userMapper.add(user1);
        ReviseServiceDto reviseServiceDto = new ReviseServiceDto("이름", 19, "man", "01000000000", "fishing");

        //when
        boolean isGetInfo = myPageService.revise("test", reviseServiceDto);
        User testUser = userMapper.getInfo("test");

        //then
        Assertions.assertThat(isGetInfo).isTrue();
        Assertions.assertThat(testUser.getId()).isEqualTo("test");
        Assertions.assertThat(testUser.getName()).isEqualTo(reviseServiceDto.getName());
        Assertions.assertThat(testUser.getAge()).isEqualTo(reviseServiceDto.getAge());
        Assertions.assertThat(testUser.getSex()).isEqualTo(reviseServiceDto.getSex());
        Assertions.assertThat(testUser.getPhone()).isEqualTo(reviseServiceDto.getPhone());
        Assertions.assertThat(testUser.getHobby()).isEqualTo(reviseServiceDto.getHobby());
    }

    @Test
    void 정보수정_아이디없음() {
        //given
        User user1 = new User("test", "test123", "test");
        User user2 = new User("test", "이름", 19, "man", "01000000000", "fishing");
        userMapper.add(user1);
        userMapper.reviseInfo(user2);

        //when
        User testUser = new User("test1", "이름", 19, "man", "01000000000", "fishing");
        boolean isGetInfo = userMapper.reviseInfo(testUser) == 1;

        //then
        Assertions.assertThat(isGetInfo).isFalse();
    }
}