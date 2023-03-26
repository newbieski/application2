package com.application2.demo.module1.web.dashboard;

import com.application2.demo.module1.config.ClanConfig;
import com.application2.demo.module1.service.dashboard.DashboardService;
import com.application2.demo.module1.web.dto.DashboardResponse1Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.application2.demo.module1.service.dashboard.*;

import java.util.*;

@RequiredArgsConstructor
@ResponseBody
@RestController
public class DashBoardController {
    @Autowired
    ClanConfig clanConfig;
    private final DashboardService dashboardService;


    @GetMapping("/dashboard")
    public List<DashboardResponse1Dto> getMembers() {
        return dashboardService.createDashboard1();
    }
    
    @GetMapping("/dashboard/wartag")
    public List<String> getWartag() {
        return dashboardService.getMonthlyWartag();
    }
}