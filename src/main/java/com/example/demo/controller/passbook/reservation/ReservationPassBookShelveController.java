package com.example.demo.controller.passbook.reservation;

import com.example.demo.domain.entity.passbook.service.reservation.ReservationPassBookShelveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/calendar/reservation")
public class ReservationPassBookShelveController {
    private final ReservationPassBookShelveService reservationPassBookShelveService;

    @GetMapping(value = "/shelve")
    public String shelve(@SessionAttribute("userId") String userId, @RequestParam("num") int num, Model model) {
        model.addAttribute("isShelve", reservationPassBookShelveService.shelveMonth(num, userId));

        return "thymeleaf/ledger/passbook/reservation/calendar-reservation-shelved";
    }
}


