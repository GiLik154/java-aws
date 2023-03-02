package com.example.demo.controller.etc;

import com.example.demo.domain.entity.passbookkinds.service.select.SelectPassBookKindsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
@SessionAttributes("pbKindsNum")
public class SelectKindsController {
    private final SelectPassBookKindsService selectPassBookKindsService;

    @GetMapping(value = "/choice")
    public String choice(@SessionAttribute String userId, @RequestParam(value = "choiceKindsNum", defaultValue = "0") int choiceKindsNum, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        session.removeAttribute("isNotChoice");

        model.addAttribute("pbKindsNum", choiceKindsNum);
        model.addAttribute("PassBookKindsList", selectPassBookKindsService.selectAll(userId));

        return "thymeleaf/ledger/main_page/calendar-choice";
    }

    @GetMapping(value = "/choice/exit")
    public String exit(SessionStatus status) {
        status.setComplete();
        return "thymeleaf/user/login";
    }
}
