package com.application2.demo.module1.service.clanwar;

import com.application2.demo.module1.domain.clanwar.ClanWarAttack;
import com.application2.demo.module1.domain.clanwar.ClanWarAttackRepository;
import com.application2.demo.module1.domain.clanwar.ClanWarSummary;
import com.application2.demo.module1.domain.clanwar.ClanWarSummaryRepository;
import com.application2.demo.module1.domain.cocapi.clanwar.CocApiClanWarAttack;
import com.application2.demo.module1.domain.cocapi.clanwar.CocApiClanWarSummary;
import com.application2.demo.module1.domain.cocapi.repository.CocCurrentWar;
import com.application2.demo.module1.web.dto.ClanWarAttackResponseDto;
import com.application2.demo.module2.code.ApiCode;
import com.application2.demo.module2.service.ApiEventService;
import com.application2.demo.module2.service.dto.ApiEventSaveRequestDto;
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
    private final Logger logger = LoggerFactory.getLogger(ClanWarService.class);

    private final CocCurrentWar cocCurrentWar;
    private final ClanWarSummaryRepository clanWarSummaryRepository;
    private final ClanWarAttackRepository clanWarAttackRepository;

    private final ApiEventService apiEventService;

    public List<CocApiClanWarAttack> readClanWarAttack() {
        cocCurrentWar.load();
        List<CocApiClanWarAttack> cocApiClanWarAttack = cocCurrentWar.getClanWarAttack();
        return cocApiClanWarAttack;
    }

    public CocApiClanWarSummary readClanWarSummary() {
        cocCurrentWar.load();
        CocApiClanWarSummary cocApiClanWarSummary = cocCurrentWar.getClanWarSummary();
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

        ApiEventSaveRequestDto dto = ApiEventSaveRequestDto.builder()
                .eventTime(cocApiClanWarSummary.getEndTime().minusMinutes(5))
                .eventCode(ApiCode.CLANWAR_SUMMARY)
                .state(cocApiClanWarSummary.getState())
                .regTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        apiEventService.saveApiEvent(dto);
    }
}
