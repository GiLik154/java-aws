package com.example.demo.controller.passbook.add;

import com.example.demo.controller.passbook.add.dto.AddPassBookDto;
import com.example.demo.controller.vo.DateVo;
import com.example.demo.domain.entity.passbook.service.add.AddPassBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/calendar/details")
public class AddPassBookController {
    private final AddPassBookService addPassBookService;

    @GetMapping(value = "/add")
    public String add() {
        return "thymeleaf/ledger/passbook/calendar-add";
    }

    @PostMapping(value = "/add-check")
    public String check(@SessionAttribute("userId") String userId, @SessionAttribute("pbKindsNum") int pbKindsNum, @RequestParam(value = "date", defaultValue = "") String date, AddPassBookDto addPassBookDto, Model model) {
        DateVo dateVo = new DateVo(date);

        model.addAttribute("isAdd", addPassBookService.add(addPassBookDto.convertServiceDto(userId, dateVo.getYear(), dateVo.getMonth(), dateVo.getDay(), pbKindsNum)));

        return "thymeleaf/ledger/passbook/calendar-add-check";
    }
}
