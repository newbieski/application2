package com.application2.demo.module1.web;

import com.application2.demo.module1.config.ClanConfig;
import com.application2.demo.module1.service.memberlist.MemberListService;
import com.application2.demo.module1.web.dto.MemberListSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@ResponseBody
@RestController
public class ClanInfoController {
    private Logger logger = LoggerFactory.getLogger(ClanInfoController.class);
    @Autowired
    ClanConfig clanConfig;
    private final MemberListService memberListService;

    @PostMapping("myclan/current/members")
    public String saveCurrentMembers() {
        memberListService.saveClanMembers();
        return "200";
    }
}
