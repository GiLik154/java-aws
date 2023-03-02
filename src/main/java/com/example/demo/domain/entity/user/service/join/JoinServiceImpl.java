package com.example.demo.domain.entity.user.service.join;

import com.example.demo.domain.entity.user.domain.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.domain.entity.user.service.dto.JoinServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinServiceImpl implements JoinService {
    private final UserMapper userMapper;
    private final PasswordEncoder bCryptPasswordEncoder;

    public boolean add(JoinServiceDto joinServiceDto) {
        User user = new User(joinServiceDto.getId(), joinServiceDto.getPw(), joinServiceDto.getName());
        if (isDuplicated(user)) {
            user.encodingPw(bCryptPasswordEncoder);
            return userMapper.add(user) == 1;
        }
        return false;
    }
    private boolean isDuplicated(User user) {
        return userMapper.selectUser(user.getId()) == 0;
    }

}

