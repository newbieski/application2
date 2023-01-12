package com.application2.demo.web.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UpdateController {
    @GetMapping("/update")
    public String update(Model model) {
        return "update";
    }
}
