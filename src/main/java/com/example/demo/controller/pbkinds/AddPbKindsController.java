package com.example.demo.controller.pbkinds;

import com.example.demo.domain.entity.passbookkinds.service.add.AddPassBookKindsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pbkinds")
public class AddPbKindsController {
    private final AddPassBookKindsService addPassBookKindsService;

    @GetMapping(value = "/add")
    public String add() {
        return "thymeleaf/ledger/pbkinds/pbkinds-add";
    }

    @PostMapping(value = "/add/check")
    public String check(@SessionAttribute("userId") String userId, @RequestParam(value = "name", defaultValue = "") String name, Model model) {
        model.addAttribute("isAdd", addPassBookKindsService.add(name, userId));

        return "thymeleaf/ledger/pbkinds/pbkinds-add-check";
    }
}
