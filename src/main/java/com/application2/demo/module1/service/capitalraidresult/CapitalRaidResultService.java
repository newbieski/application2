package com.application2.demo.module1.service.capitalraidresult;
import com.application2.demo.module1.domain.capitalraidresult.CapitalRaidResult;
import com.application2.demo.module1.domain.capitalraidresult.CapitalRaidResultRepository;
import com.application2.demo.module1.domain.cocapi.capitalraid.CocApiCapitalRaid;
import com.application2.demo.module1.domain.cocapi.capitalraid.CocApiCapitalRaidSummary;
import com.application2.demo.module1.domain.cocapi.repository.CocCapitalRaid;
import com.application2.demo.module1.web.dto.CapitalRaidResponseDto;
import com.application2.demo.module2.code.ApiCode;
import com.application2.demo.module2.service.ApiEventService;
import com.application2.demo.module2.service.dto.ApiEventSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

import java.time.ZoneOffset;

@RequiredArgsConstructor
@Service
public class CapitalRaidResultService {
    private final Logger logger = LoggerFactory.getLogger(CapitalRaidResultService.class);
    private final CocCapitalRaid cocCapitalRaid;
    private final CapitalRaidResultRepository capitalRaidResultRepository;

    private final ApiEventService apiEventService;

    public void saveCapitalRaid() {
        cocCapitalRaid.load();
        CocApiCapitalRaidSummary summary = cocCapitalRaid.getCapitalRaidSummary();
        List<CocApiCapitalRaid> result = cocCapitalRaid.getCapitalRaid();
        for (CocApiCapitalRaid val : result) {
            capitalRaidResultRepository.save(val.toEntity());
        }
        apiEventService.saveApiEvent(ApiEventSaveRequestDto.builder()
                        .eventTime(summary.getEndTime().minusMinutes(5))
                        .eventCode(ApiCode.CAPITALRAID_RESULT)
                        .state(summary.getState())
                        .regTime(LocalDateTime.now(ZoneOffset.UTC))
                        .build());
    }
    
    public CapitalRaidResponseDto getCurrentMonthSummary() {
        CapitalRaidResponseDto resDto = new CapitalRaidResponseDto();
        LocalDateTime start = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).atStartOfDay();
        LocalDate end1 = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(LocalDate.now().lengthOfMonth());
        LocalDateTime end = LocalDateTime.parse(end1.toString()+"T23:59:59");
        
        List<CapitalRaidResult> result = capitalRaidResultRepository.findAllByEndTimeBetween(start, end);

        if (result.isEmpty()) {
            return resDto;
        }
        Collections.sort(result, new Comparator<CapitalRaidResult>() {
            @Override
            public int compare(CapitalRaidResult o1, CapitalRaidResult o2) {
                return o2.getRegTime().compareTo(o1.getRegTime());
            }
        });
        LocalDateTime round = result.get(0).getEndTime();
        LocalDateTime regTime = result.get(0).getRegTime();
        for (int i = 0 ; i < result.size() ; i++) {
            CapitalRaidResult cur = result.get(i);
            if (cur.getEndTime().compareTo(round) != 0) {
                round = cur.getEndTime();
                regTime = cur.getRegTime();
            }
            if (cur.getEndTime().compareTo(round) == 0) {
                if (cur.getRegTime().compareTo(regTime) == 0) {
                    resDto.add(cur.getTag(), cur.getAttackCount());
                }
            }
        }
        
        return resDto;
    }
}
