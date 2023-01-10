package com.application2.demo.service.dashboard;

import com.application2.demo.service.clanwarleagueattack.ClanWarLeagueAttackService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.time.LocalDateTime;

import com.application2.demo.web.dto.*;
import com.application2.demo.service.memberlist.*;
import com.application2.demo.service.capitalraidresult.*;
import com.application2.demo.service.clanwarleaguewar.*;

@RequiredArgsConstructor
@Service

public class DashboardService {
    @Autowired
    private final MemberListService memberListService;
    private final CapitalRaidResultService capitalRaidResultService;
    private final ClanWarLeagueWarService clanWarLeagueWarService;
    private final ClanWarLeagueAttackService clanWarLeagueAttackService;

    private Logger logger = LoggerFactory.getLogger(DashboardService.class);

    
    public List<DashboardResponse1Dto> createDashboard1() {
        logger.info("createDashboard1");
        List<DashboardResponse1Dto> response = new ArrayList<>();
        List<MemberListResponseDto> memberList = memberListService.readLatest();
        CapitalRaidResponseDto capitalResponse = capitalRaidResultService.getCurrentMonthSummary();
        ClanWarLeagueAttackResonseDto clanwarleagueResponse = clanWarLeagueAttackService.readLatest(clanWarLeagueWarService.getMonthlyWarTag());
        
        HashMap<String, DashboardResponse1Dto> map = new HashMap<String, DashboardResponse1Dto>();
        for (int i = 0 ; i < memberList.size() ; i++) {
            MemberListResponseDto cur = memberList.get(i);
            String tag = cur.getTag();
            response.add(DashboardResponse1Dto.builder()
                            .name(cur.getName())
                            .tag(tag)
                            .capitalRaidAttackCount(capitalResponse.getAttackCount(tag))
                            .clanwarlegueUsedAttackCount(clanwarleagueResponse.getUsed(tag))
                            .clanwarlegueTotalAttackCount(clanwarleagueResponse.getTotal(tag))
                            .clanwarleagueStars(clanwarleagueResponse.getStars(tag))
                            .build());
        }
        
        return response;
    }
    
    public List<String> getMonthlyWartag() {
        return clanWarLeagueWarService.getMonthlyWarTag();
    }
}