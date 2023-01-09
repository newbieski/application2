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

import com.application2.demo.web.dto.MemberListResponseDto;
import com.application2.demo.service.memberlist.MemberListService;

import java.util.*;

@RequiredArgsConstructor
@ResponseBody
@RestController


public class DashBoardController {
    @Autowired
    ClanConfig clanConfig;
    private final MemberListService memberListService;
    
    @GetMapping("/dashboard")
    public String getMembers() {
        List<MemberListResponseDto> members = memberListService.readLatest();
        StringBuffer sb = new StringBuffer();
        for (MemberListResponseDto cur : members) {
            sb.append(cur.getName())
                .append(" ")
                .append(cur.getTag())
                .append(" ")
                .append(cur.getRegTime())
                .append("<br>");
        }
        return sb.toString();
    }
}