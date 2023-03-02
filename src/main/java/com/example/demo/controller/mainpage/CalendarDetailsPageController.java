package com.example.demo.controller.mainpage;

import com.example.demo.controller.vo.DateVo;
import com.example.demo.mapper.PassBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("calendar/details")
public class CalendarDetailsPageController {
    private final PassBookMapper passBookMapper;

    @GetMapping(value = "")
    public String outputDaily(@SessionAttribute("userId") String userId, @SessionAttribute("pbKindsNum") int pbKindsNum, @RequestParam(value = "date", defaultValue = "") String date, Model model) {
        DateVo dateVo = new DateVo(date);

        model.addAttribute("passBookList", passBookMapper.selectDaily(userId, dateVo.getYear(), dateVo.getMonth(), dateVo.getDay(), pbKindsNum));

        return "thymeleaf/ledger/main_page/calendar-details";
    }

    @GetMapping(value = "/month")
    public String outputMonthly(@SessionAttribute("userId") String userId, @SessionAttribute("pbKindsNum") int pbKindsNum, @RequestParam(value = "date", defaultValue = "") String date, Model model) {
        DateVo dateVo = new DateVo(date);

        model.addAttribute("passBookListMonthly", passBookMapper.selectMonthly(userId, dateVo.getYear(), dateVo.getMonth(), pbKindsNum));

        return "thymeleaf/ledger/main_page/calendar-details-month";
    }

    @GetMapping(value = "/year")
    public String outputYearly(@SessionAttribute("userId") String userId, @SessionAttribute("pbKindsNum") int pbKindsNum, @RequestParam(value = "date", defaultValue = "") String date, Model model) {
        DateVo dateVo = new DateVo(date);

        model.addAttribute("passBookListYearly", passBookMapper.selectYearly(userId, dateVo.getYear(), pbKindsNum));

        return "thymeleaf/ledger/main_page/calendar-details-year";
    }
}