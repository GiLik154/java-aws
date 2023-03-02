package com.example.demo.controller.user;

import com.example.demo.controller.user.dto.LoginDto;
import com.example.demo.domain.entity.user.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
@SessionAttributes("userId")
@RequestMapping(value = "/login")
public class LoginController {
    private final LoginService loginService;

    @GetMapping(value = "")
    public String login(SessionStatus status) {
        status.setComplete();

        return "thymeleaf/user/login";
    }

    @PostMapping(value = "/check")
    public String check(LoginDto loginDto, Model model) {
        if (loginService.isCompare(loginDto.getId(), loginDto.getPw())) {
            model.addAttribute("isLogin", true);
            model.addAttribute("userId", loginDto.getId());
        } else {
            model.addAttribute("isLogin", false);
        }

        return "thymeleaf/user/login-check";
    }
}