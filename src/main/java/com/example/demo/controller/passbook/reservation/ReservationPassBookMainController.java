package com.example.demo.controller.passbook.reservation;

import com.example.demo.domain.entity.passbook.service.reservation.ReservationPassBookMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/calendar/reservation")
public class ReservationPassBookMainController {
    private final ReservationPassBookMainService reservationPassBookMainService;

    @GetMapping(value = "")
    public String outputList(@SessionAttribute("userId") String userId, @SessionAttribute("pbKindsNum") int pbKindsNum, Model model) {
        model.addAttribute("reservationPassBookList", reservationPassBookMainService.selectList(userId, pbKindsNum));

        return "thymeleaf/ledger/passbook/reservation/calendar-reservation";
    }

    @GetMapping(value = "/all")
    public String outputAllList(@SessionAttribute("userId") String userId, @SessionAttribute("pbKindsNum") int pbKindsNum, Model model) {
        model.addAttribute("reservationPassBookListAll", reservationPassBookMainService.selectAll(userId, pbKindsNum));

        return "thymeleaf/ledger/passbook/reservation/calendar-reservation-all";
    }
}