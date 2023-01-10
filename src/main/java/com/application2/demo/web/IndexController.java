package com.application2.demo.web;

import com.application2.demo.service.dashboard.DashboardService;
import com.application2.demo.web.dto.DashboardResponse1Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
