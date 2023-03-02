package com.example.demo.controller.etc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/reset")
public class ResetPageController {

    @GetMapping(value = "/isNotChoice")
    public String resetNotChoice(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("isNotChoice");

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping(value = "/isNotLogin")
    public String resetNotLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("isNotLogin");

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
