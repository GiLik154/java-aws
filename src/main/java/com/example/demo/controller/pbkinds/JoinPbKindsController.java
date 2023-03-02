package com.example.demo.controller.pbkinds;

import com.example.demo.domain.entity.passbookkinds.service.join.JoinUserPassBookKindsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pbkinds")
public class JoinPbKindsController {
    private final JoinUserPassBookKindsService joinUserPassBookKindsService;

    @GetMapping(value = "/join")
    public String join() {
        return "thymeleaf/ledger/pbkinds/pbkinds-join";
    }

    @PostMapping(value = "/join/check")
    public String check(@SessionAttribute String userId, @RequestParam String token, Model model) {
        model.addAttribute("isJoin", joinUserPassBookKindsService.join(userId, token));
        return "thymeleaf/ledger/pbkinds/pbkinds-join-check";
    }
}