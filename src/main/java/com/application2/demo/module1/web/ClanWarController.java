package com.application2.demo.module1.web;

import com.application2.demo.module1.domain.cocapi.clanwar.CocApiClanWarAttack;
import com.application2.demo.module1.domain.cocapi.clanwar.CocApiClanWarSummary;
import com.application2.demo.module1.service.clanwar.ClanWarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@ResponseBody
@RestController
public class ClanWarController {
    @Autowired
    ClanWarService clanWarService;

    @GetMapping("/clanwar/summary")
    public CocApiClanWarSummary getClanWarSummary() {
        return clanWarService.readClanWarSummary();
    }

    @GetMapping("/clanwar/attack")
    public List<CocApiClanWarAttack> getClanWarAttack() {
        return clanWarService.readClanWarAttack();
    }

    @PostMapping("/clanwar")
    public String saveClanWar() {
        clanWarService.saveClanWar();
        return "200";
    }
}
