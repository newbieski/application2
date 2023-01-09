package com.application2.demo.service.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.time.LocalDateTime;

import com.application2.demo.web.dto.*;
import com.application2.demo.service.memberlist.*;
import com.application2.demo.service.capitalraidresult.*;

@RequiredArgsConstructor
@Service

public class DashboardService {
    @Autowired
    private final MemberListService memberListService;
    private final CapitalRaidResultService capitalRaidResultService;
    
    public List<DashboardResponse1Dto> createDashboard1() {
        List<DashboardResponse1Dto> response = new ArrayList<>();
        List<MemberListResponseDto> memberList = memberListService.readLatest();
        CapitalRaidResponseDto capitalResponse = capitalRaidResultService.getCurrentMonthSummary();
        
        HashMap<String, DashboardResponse1Dto> map = new HashMap<String, DashboardResponse1Dto>();
        HashMap<String, Long> capitalAttackCount = capitalResponse.getMap();
        for (int i = 0 ; i < memberList.size() ; i++) {
            MemberListResponseDto cur = memberList.get(i);
            String tag = cur.getTag();
            response.add(DashboardResponse1Dto.builder()
                                    .name(cur.getName())
                                    .tag(tag)
                                    .capitalRaidAttackCount(capitalAttackCount.getOrDefault(tag, Long.valueOf(0)))
                                    .build());
        }
        
        
        return response;
    }
}