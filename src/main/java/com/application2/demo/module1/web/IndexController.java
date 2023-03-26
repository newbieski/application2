package com.application2.demo.module1.web;

import com.application2.demo.module1.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    @Autowired
    private final DashboardService dashboardService;
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("dashboard1", dashboardService.createDashboard1());
        return "index";
    }
}
