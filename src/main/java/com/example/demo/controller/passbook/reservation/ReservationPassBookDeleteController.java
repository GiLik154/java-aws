package com.example.demo.controller.passbook.reservation;

import com.example.demo.domain.entity.passbook.service.reservation.ReservationPassBookDeleteService;
import com.example.demo.domain.entity.passbook.service.reservation.ReservationPassBookMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/calendar/reservation/delete")
@RequiredArgsConstructor
public class ReservationPassBookDeleteController {
    private final ReservationPassBookDeleteService reservationPassBookDeleteService;
    private final ReservationPassBookMainService reservationPassBookMainService;

    @GetMapping(value = "")
    public String delete(@SessionAttribute("userId") String userId, @SessionAttribute("pbKindsNum") int pbKindsNum, Model model) {
        model.addAttribute("reservationPassBookListAll", reservationPassBookMainService.selectAll(userId, pbKindsNum));

        return "thymeleaf/ledger/passbook/reservation/calendar-reservation-delete";
    }

    @GetMapping(value = "/check")
    public String check(@SessionAttribute("userId") String userId, @RequestParam("num") int num, Model model) {
        model.addAttribute("isDelete", reservationPassBookDeleteService.delete(num, userId));

        return "thymeleaf/ledger/passbook/reservation/calendar-reservation-delete-check";
    }

}