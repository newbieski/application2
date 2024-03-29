package com.application2.demo.module1.service.clanwarleaguewar;

import com.application2.demo.module1.domain.clanwarleaguewar.*;
import com.application2.demo.module1.config.ClanConfig;
import com.application2.demo.module1.domain.clanwarleaguewar.ClanWarLeagueWar;
import com.application2.demo.module1.domain.clanwarleaguewar.ClanWarLeagueWarRepository;
import com.application2.demo.module1.web.dto.ClanWarLeagueWarSaveRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Service
public class ClanWarLeagueWarService {
    private final ClanWarLeagueWarRepository clanWarLeagueWarRepository;
    private final ClanConfig clanConfig;

    @Transactional
    public Long save(ClanWarLeagueWarSaveRequestDto requestDto) {
        return clanWarLeagueWarRepository.save(requestDto.toEntity()).getId();
    }
    
    public List<String> getMonthlyWarTag() {
        LocalDateTime start = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).atStartOfDay();
        LocalDate end1 = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(LocalDate.now().lengthOfMonth());
        LocalDateTime end = LocalDateTime.parse(end1.toString()+"T23:59:59");
        List<ClanWarLeagueWar> result = clanWarLeagueWarRepository.findAllByEndTimeBetween(start, end);
        List<String> response = new ArrayList<>();
        if (result.isEmpty()) {
            return response;
        }
        Collections.sort(result, new Comparator<ClanWarLeagueWar>() {
           @Override
           public int compare(ClanWarLeagueWar o1, ClanWarLeagueWar o2) {
               return o2.getRegTime().compareTo(o1.getRegTime());
           }
        });
        String mytag = "#"+clanConfig.getCLAN_TAG().substring(3).toUpperCase();

        LocalDateTime base = result.get(0).getRegTime();
        for (int i = 0 ; i < result.size() ; i++) {
            ClanWarLeagueWar cur = result.get(i);
            if (cur.getRegTime().compareTo(base) != 0) {
                break;
            }
            if (cur.getClanTag().equals(mytag) || cur.getOpponentTag().equals(mytag)) {
                response.add(cur.getWarTag());
            }
            //response.add(cur.getWarTag() + " " + cur.getClanTag() + " " + cur.getOpponentTag() + " " + mytag);
        }
        return response;
    }
}
