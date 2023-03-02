package com.example.demo.controller.mainpage;

import com.example.demo.controller.mainpage.dto.CalendarDto;
import com.example.demo.controller.vo.DateVo;
import com.example.demo.domain.entity.passbookkinds.service.select.SelectPassBookKindsService;
import com.example.demo.domain.entity.passbook.service.mainpage.MainPageService;
import com.example.demo.domain.entity.passbook.service.sidepage.MonthlyPassBookService;
import com.example.demo.domain.entity.passbook.service.sidepage.TotalPassBookService;
import com.example.demo.domain.entity.passbook.service.sidepage.YearlyPassBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
@SessionAttributes("pbKindsNum")
public class CalendarMainPageController {
    private final MainPageService mainPageService;
    private final TotalPassBookService totalPassBookService;
    private final MonthlyPassBookService monthlyPassBookService;
    private final YearlyPassBookService yearlyPassBookService;
    private final SelectPassBookKindsService selectPassBookKindsService;

    @GetMapping(value = "/main")
    public String run(@SessionAttribute("userId") String userId, @RequestParam(value = "date", defaultValue = "") String date, @SessionAttribute int pbKindsNum, Model model) {
        DateVo dateVo = new DateVo(date);
        model.addAttribute("PassBookKindsList", selectPassBookKindsService.selectAll(userId));
        model.addAttribute("checkReservation", mainPageService.checkReservation(userId, pbKindsNum));
        model.addAttribute("calendarDto", new CalendarDto(dateVo.findMaxDay(), dateVo.findFirstDayOfWeek(), dateVo.sumYearAndMonth()));
        model.addAttribute("totalByDayList", mainPageService.selectTotalByDayList(userId, dateVo.getYear(), dateVo.getMonth(), pbKindsNum));
        model.addAttribute("totalPassBookDto", totalPassBookService.getTotalPassBookDto(userId, dateVo.getYear(), dateVo.getMonth(), pbKindsNum));
        model.addAttribute("monthlyPassBookDto", monthlyPassBookService.getMonthlyPassBookDto(userId, dateVo.getYear(), dateVo.getMonth(), pbKindsNum));
        model.addAttribute("yearlyPassBookDto", yearlyPassBookService.getYearlyPassBookDto(userId, dateVo.getYear(), pbKindsNum));

        return "thymeleaf/ledger/main_page/calendar-main";
    }

    @GetMapping(value = "/exit")
    public String exit(SessionStatus status) {
        status.setComplete();
        return "thymeleaf/user/login";
    }
}