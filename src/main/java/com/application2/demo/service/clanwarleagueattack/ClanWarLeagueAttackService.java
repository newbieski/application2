package com.application2.demo.service.clanwarleagueattack;

import com.application2.demo.domain.clanwarleagueattack.ClanWarLeagueAttack;
import com.application2.demo.domain.clanwarleagueattack.ClanWarLeagueAttackRepository;
import com.application2.demo.web.dto.ClanWarLeagueAttackResonseDto;
import com.application2.demo.web.dto.ClanWarLeagueAttackSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ClanWarLeagueAttackService {
    private Logger logger = LoggerFactory.getLogger(ClanWarLeagueAttackService.class);
    private final ClanWarLeagueAttackRepository clanWarLeagueAttackRepository;

    @Transactional
    public Long save(ClanWarLeagueAttackSaveRequestDto requestDto) {
        return clanWarLeagueAttackRepository.save(requestDto.toEntity()).getId();
    }

    public ClanWarLeagueAttackResonseDto readLatest(List<String> warTags) {
        logger.info("readLatest");
        ClanWarLeagueAttackResonseDto responseDto = new ClanWarLeagueAttackResonseDto();
        for (String warTag : warTags) {
            List<ClanWarLeagueAttack> res =  clanWarLeagueAttackRepository.findAllByWarTag(warTag);
            if (res.isEmpty()) {
                continue;
            }
            Collections.sort(res, new Comparator<ClanWarLeagueAttack>() {
                @Override
                public int compare(ClanWarLeagueAttack o1, ClanWarLeagueAttack o2) {
                    return o2.getRegTime().compareTo(o1.getRegTime());
                }
            });
            LocalDateTime base = res.get(0).getRegTime();
            logger.info("base reg time :" + base.toString());
            for (int i = 0 ; i < res.size() ; i++) {
                ClanWarLeagueAttack cur = res.get(i);
                if (cur.getRegTime().compareTo(base) != 0) {
                    break;
                }
                String attackerTag = cur.getAttackerTag();
                long total = 1, used = 0, stars = cur.getStars();
                if (cur.getDefenderTag() != null) {
                    used = 1;
                }
                responseDto.addTotal(attackerTag, total);
                responseDto.addUsed(attackerTag, used);
                responseDto.addStars(attackerTag, stars);
            }
        }
        return responseDto;
    }

}
