package com.example.demo.domain.entity.user.service.mypage;

import com.example.demo.domain.entity.user.domain.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.domain.entity.user.service.dto.ReviseServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageServiceImpl implements MyPageService {
    private final UserMapper userMapper;

    public User getInfo(String userId) {
        return userMapper.getInfo(userId);
    }

    public boolean revise(String userId, ReviseServiceDto reviseServiceDto) {
        User user = new User(userId,
                reviseServiceDto.getName(),
                reviseServiceDto.getAge(),
                reviseServiceDto.getSex(),
                reviseServiceDto.getPhone(),
                reviseServiceDto.getHobby());
        return userMapper.reviseInfo(user) == 1;
    }
}

