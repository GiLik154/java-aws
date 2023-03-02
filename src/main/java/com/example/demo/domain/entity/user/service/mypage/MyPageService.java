package com.example.demo.domain.entity.user.service.mypage;

import com.example.demo.domain.entity.user.domain.User;
import com.example.demo.domain.entity.user.service.dto.ReviseServiceDto;

public interface MyPageService {
    User getInfo(String userId);
    boolean revise(String userId, ReviseServiceDto reviseServiceDto);
}

