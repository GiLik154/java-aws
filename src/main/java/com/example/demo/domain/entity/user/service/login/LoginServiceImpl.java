package com.example.demo.domain.entity.user.service.login;

import com.example.demo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl implements LoginService {
    private final UserMapper userMapper;
    private final PasswordEncoder bCryptPasswordEncoder;
    public boolean isCompare(String id, String pw) {
        return bCryptPasswordEncoder.matches(pw,userMapper.getPw(id));
    }
}