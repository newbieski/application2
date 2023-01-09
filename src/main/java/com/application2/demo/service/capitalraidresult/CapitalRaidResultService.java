package com.application2.demo.service.capitalraidresult;
import com.application2.demo.domain.capitalraidresult.*;
import com.application2.demo.web.dto.CapitalRaidResultSaveRequestDto;
import com.application2.demo.web.dto.CapitalRaidResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

import java.time.ZoneOffset;

@RequiredArgsConstructor
@Service
public class CapitalRaidResultService {
    private final CapitalRaidResultRepository capitalRaidResultRepository;

    @Transactional
    public Long save(CapitalRaidResultSaveRequestDto CapitalRaidResultSaveRequestDto) {
        return capitalRaidResultRepository.save(CapitalRaidResultSaveRequestDto.toEntity()).getId();
    }
    
    public CapitalRaidResponseDto getCurrentMonthSummary() {
        CapitalRaidResponseDto resDto = new CapitalRaidResponseDto();
        LocalDateTime start = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).atStartOfDay();
        LocalDate end1 = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(LocalDate.now().lengthOfMonth());
        LocalDateTime end = LocalDateTime.parse(end1.toString()+"T23:59:59");
        
        List<CapitalRaidResult> result = capitalRaidResultRepository.findAllByEndTimeBetween(start, end);
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
