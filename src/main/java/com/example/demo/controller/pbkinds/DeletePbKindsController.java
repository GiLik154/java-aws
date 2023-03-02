package com.example.demo.controller.pbkinds;

import com.example.demo.mapper.PassBookKindsMapper;
import com.example.demo.domain.entity.passbookkinds.service.delete.DeletePassBookKindsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pbkinds")
public class DeletePbKindsController {
    private final DeletePassBookKindsService deletePassBookKindsService;
    private final PassBookKindsMapper passBookKindsMapper;

    @GetMapping(value = "/delete")
    public String delete(@SessionAttribute("pbKindsNum") int pbKindsNum, Model model) {
        model.addAttribute("pbKindsList", passBookKindsMapper.selectAllByPbKindsNum(pbKindsNum));

        return "thymeleaf/ledger/pbkinds/pbkinds-delete";
    }

    @PostMapping(value = "/delete/check")
    public String check(@SessionAttribute("userId") String userId, @SessionAttribute("pbKindsNum") int pbKindsNum, Model model) {
        model.addAttribute("isDelete", deletePassBookKindsService.delete(userId, pbKindsNum));

        return "thymeleaf/ledger/pbkinds/pbkinds-delete-check";
    }
}
