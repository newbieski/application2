package com.application2.demo.service.clanwar;

import com.application2.demo.domain.clanwar.ClanWarAttack;
import com.application2.demo.domain.clanwar.ClanWarAttackRepository;
import com.application2.demo.domain.clanwar.ClanWarSummary;
import com.application2.demo.domain.clanwar.ClanWarSummaryRepository;
import com.application2.demo.domain.cocapi.clanwar.CocApiClanWarAttack;
import com.application2.demo.domain.cocapi.clanwar.CocApiClanWarSummary;
import com.application2.demo.domain.cocapi.repository.CocCurrentWar;
import com.application2.demo.web.dto.ClanWarAttackResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ClanWarService {
    private Logger logger = LoggerFactory.getLogger(ClanWarService.class);

    private final CocCurrentWar cocCurrentWar;
    private final ClanWarSummaryRepository clanWarSummaryRepository;
    private final ClanWarAttackRepository clanWarAttackRepository;

    public List<CocApiClanWarAttack> readClanWarAttack() {
        cocCurrentWar.load();
        CocApiClanWarSummary cocApiClanWarSummary = cocCurrentWar.getClanWarSummary();
        List<CocApiClanWarAttack> cocApiClanWarAttack = cocCurrentWar.getClanWarAttack();
        return cocApiClanWarAttack;
    }

    public CocApiClanWarSummary readClanWarSummary() {
        cocCurrentWar.load();
        CocApiClanWarSummary cocApiClanWarSummary = cocCurrentWar.getClanWarSummary();
        List<CocApiClanWarAttack> cocApiClanWarAttack = cocCurrentWar.getClanWarAttack();
        return cocApiClanWarSummary;
    }

    public ClanWarAttackResponseDto readClanWarAttackCurrentMonth() {
        ClanWarAttackResponseDto responseDto = new ClanWarAttackResponseDto();
        LocalDateTime start = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).atStartOfDay();
        LocalDate end1 = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(LocalDate.now().lengthOfMonth());
        LocalDateTime end = LocalDateTime.parse(end1.toString()+"T23:59:59");
        List<ClanWarSummary> clanWarSummaryList = clanWarSummaryRepository.findAllByEndTimeBetween(start, end);

        if (clanWarSummaryList.isEmpty()) {
            return responseDto;
        }
        Collections.sort(clanWarSummaryList, new Comparator<ClanWarSummary>() {
            @Override
            public int compare(ClanWarSummary o1, ClanWarSummary o2) {
                return o2.getRegTime().compareTo(o1.getRegTime());
            }
        });
        LocalDateTime round = clanWarSummaryList.get(0).getEndTime();
        LocalDateTime regTime = clanWarSummaryList.get(0).getRegTime();
        for (ClanWarSummary clanWarSummary : clanWarSummaryList) {
            if (clanWarSummary.getEndTime().compareTo(round) != 0) {
                round = clanWarSummary.getEndTime();
                regTime = clanWarSummary.getRegTime();
            }
            Set<String> set = new HashSet<String>();
            if (clanWarSummary.getRegTime().compareTo(regTime) == 0 && !clanWarSummary.getState().equals("preparation")) {
                long clanwarId = clanWarSummary.getId();
                List<ClanWarAttack> attacks = clanWarAttackRepository.findAllByclanwarId(clanwarId);
                for (ClanWarAttack attack : attacks) {
                    String tag = attack.getTag();
                    long stars = attack.getStars();
                    String dependerTag = attack.getDefenderTag();
                    if (dependerTag != null) {
                        responseDto.addUsed(tag, 1);
                        responseDto.addStars(tag, stars);
                    }
                    set.add(tag);
                }
            }
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String tag = it.next();
                responseDto.addTotal(tag, 2);
            }
        }

        return responseDto;
    }

    public void saveClanWar() {
        cocCurrentWar.load();
        CocApiClanWarSummary cocApiClanWarSummary = cocCurrentWar.getClanWarSummary();
        List<CocApiClanWarAttack> cocApiClanWarAttack = cocCurrentWar.getClanWarAttack();

        long clanwarId = clanWarSummaryRepository.save(cocApiClanWarSummary.toEntity()).getId();
        if (!cocApiClanWarSummary.getState().equals("preparation")) {
            for (CocApiClanWarAttack attack : cocApiClanWarAttack) {
                clanWarAttackRepository.save(attack.toEntity(clanwarId));
            }
        }
    }
}
