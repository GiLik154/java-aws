package com.example.demo.controller.passbook.delete;

import com.example.demo.controller.vo.DateVo;
import com.example.demo.domain.entity.passbook.service.delete.DeletePassBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/calendar/details")
public class DeletePassBookController {
    private final DeletePassBookService deletePassBookService;

    @GetMapping(value = "/delete")
    public String delete(@SessionAttribute("userId") String userId, @SessionAttribute("pbKindsNum") int pbKindsNum, @RequestParam(value = "date", defaultValue = "") String date, Model model) {
        model.addAttribute("passBookList", deletePassBookService.getDaily(userId, new DateVo(date).convertedDto(), pbKindsNum));

        return "thymeleaf/ledger/passbook/calendar-delete";
    }

    @GetMapping(value = "/delete-check")
    public String check(@SessionAttribute("userId") String userId,@RequestParam("num") int num, Model model) {
        model.addAttribute("isDelete", deletePassBookService.deletePassBook(num, userId));

        return "thymeleaf/ledger/passbook/calendar-delete-check";
    }
}
