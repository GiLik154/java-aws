package com.example.demo.controller.user;

import com.example.demo.controller.user.dto.ReviseDto;
import com.example.demo.domain.entity.user.service.mypage.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my-page")
public class MyPageController {
    public final MyPageService myPageService;

    @GetMapping(value = "")
    public String outputInfo(@SessionAttribute("userId") String userID, Model model) {
        model.addAttribute("userInfo", myPageService.getInfo(userID));

        return "thymeleaf/user/my-page";
    }

    @GetMapping(value = "/info-revise")
    public String revise(@SessionAttribute("userId") String userID, Model model) {
        model.addAttribute("userInfo", myPageService.getInfo(userID));

        return "thymeleaf/user/info-revise";
    }

    @PostMapping(value = "/revise-check")
    public String check(@SessionAttribute("userId") String userID, ReviseDto reviseDto, Model model) {
        model.addAttribute("isRevise", myPageService.revise(userID, reviseDto.convertServiceDto()));

        return "thymeleaf/user/revise-check";
    }
}