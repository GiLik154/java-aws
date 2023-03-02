package com.example.demo.controller.user;

import com.example.demo.controller.user.dto.JoinDto;
import com.example.demo.domain.entity.user.service.join.JoinService;
import com.example.demo.domain.entity.user.service.dto.JoinServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindException;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/join")
public class JoinController {
    private final JoinService joinService;

    @GetMapping(value = "")
    public String join() {
        return "thymeleaf/user/join";
    }

    @PostMapping(value = "/check")
    public String check(@Valid JoinDto joinDto, Model model) {
        model.addAttribute("isJoined", joinService.add(new JoinServiceDto(joinDto.getId(), joinDto.getPw(), joinDto.getName())));

        return "thymeleaf/user/join-check";
    }

    @ExceptionHandler(BindException.class)
    private String error(Model model) {
        model.addAttribute("errorMsg", "아이디 또는 비밀번호 길이를 확인해주세요.");

        return "thymeleaf/user/join-error";
    }
}