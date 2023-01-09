package com.application2.demo.web.dashboard;

import com.application2.demo.config.ClanConfig;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.application2.demo.service.dashboard.*;
import com.application2.demo.web.dto.DashboardResponse1Dto;

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