package com.example.demo.controller.pbkinds;

import com.example.demo.mapper.PassBookKindsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/pbkinds")
@RequiredArgsConstructor
public class TokenPbKindsController {
    private final PassBookKindsMapper passBookKindsMapper;

    @GetMapping(value = "/token")
    public String printToken(@SessionAttribute String userId, Model model) {
        model.addAttribute("tokenList", passBookKindsMapper.selectAllByUserId(userId));

        return "thymeleaf/ledger/pbkinds/pbkinds-token";
    }

}