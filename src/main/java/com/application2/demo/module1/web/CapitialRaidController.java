package com.application2.demo.module1.web;

import com.application2.demo.module1.config.ClanConfig;
import com.application2.demo.module1.service.capitalraidresult.CapitalRaidResultService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@ResponseBody
@RestController
public class CapitialRaidController {
    private Logger logger = LoggerFactory.getLogger(CapitialRaidController.class);
    @Autowired
    ClanConfig clanConfig;
    private final CapitalRaidResultService capitalRaidResultService;

    @PostMapping("/capitalraid")
    public String capitalraid() {
        capitalRaidResultService.saveCapitalRaid();
        return "200";
    }
}
