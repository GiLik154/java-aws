package com.example.demo.controller.passbook.reservation;

import com.example.demo.domain.entity.passbook.service.reservation.ReservationPassBookRegisterService;
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
public class ReservationPassBookRegisterController {
    private final ReservationPassBookRegisterService reservationPassBookRegisterService;

    @GetMapping(value = "/check")
    public String checkRegister(@SessionAttribute("userId") String userId, @SessionAttribute("pbKindsNum") int pbKindsNum, @RequestParam("num") int num, Model model) {
        model.addAttribute("reservationPassBookEach", reservationPassBookRegisterService.selectEach(num, userId, pbKindsNum));

        return "thymeleaf/ledger/passbook/reservation/calendar-reservation-check";
    }

    @GetMapping(value = "/register")
    public String register(@SessionAttribute("userId") String userId, @SessionAttribute("pbKindsNum") int pbKindsNum, @RequestParam("num") int num, Model model) {
        model.addAttribute("isRegister", reservationPassBookRegisterService.register(num, userId, pbKindsNum));

        return "thymeleaf/ledger/passbook/reservation/calendar-reservation-register";
    }

}
