package com.application2.demo.module1.service.dashboard;

import com.application2.demo.module1.service.capitalraidresult.CapitalRaidResultService;
import com.application2.demo.module1.service.clanwar.ClanWarService;
import com.application2.demo.module1.service.clanwarleagueattack.ClanWarLeagueAttackService;
import com.application2.demo.module1.service.clanwarleaguewar.ClanWarLeagueWarService;
import com.application2.demo.module1.service.memberlist.MemberListService;
import com.application2.demo.module1.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import com.application2.demo.module1.web.dto.*;
import com.application2.demo.module1.service.memberlist.*;
import com.application2.demo.module1.service.capitalraidresult.*;
import com.application2.demo.module1.service.clanwarleaguewar.*;

@RequiredArgsConstructor
@Service

public class DashboardService {
    @Autowired
    private final MemberListService memberListService;
    private final CapitalRaidResultService capitalRaidResultService;
    private final ClanWarLeagueWarService clanWarLeagueWarService;
    private final ClanWarLeagueAttackService clanWarLeagueAttackService;
    private final ClanWarService clanWarService;

    private Logger logger = LoggerFactory.getLogger(DashboardService.class);

    
    public List<DashboardResponse1Dto> createDashboard1() {
        logger.info("createDashboard1");
        List<DashboardResponse1Dto> response = new ArrayList<>();
        List<MemberListResponseDto> memberList = memberListService.readLatest();
        CapitalRaidResponseDto capitalResponse = capitalRaidResultService.getCurrentMonthSummary();
        ClanWarLeagueAttackResonseDto clanwarleagueResponse = clanWarLeagueAttackService.readLatest(clanWarLeagueWarService.getMonthlyWarTag());
        ClanWarAttackResponseDto clanwarAttackResponse = clanWarService.readClanWarAttackCurrentMonth();
        
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
                            .clanwarUsedAttackCount(clanwarAttackResponse.getUsed(tag))
                            .clanwarTotalAttackCount(clanwarAttackResponse.getTotal(tag))
                            .clanwarStars(clanwarAttackResponse.getStars(tag))
                            .role(cur.getRole())
                            .donations(cur.getDonations())
                            .donationsReceived(cur.getDonationsReceived())
                            .build());
        }
        Collections.sort(response, new Comparator<DashboardResponse1Dto>() {
            @Override
            public int compare(DashboardResponse1Dto o1, DashboardResponse1Dto o2) {
                if (o1.getMemberPoint() > o2.getMemberPoint()) {
                    return -1;
                }
                else if (o1.getMemberPoint() == o2.getMemberPoint()) {
                    return 0;
                }
                return 1;
            }
        });
        
        return response;
    }
    
    public List<String> getMonthlyWartag() {
        return clanWarLeagueWarService.getMonthlyWarTag();
    }
}