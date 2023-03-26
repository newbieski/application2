package com.application2.demo.module2.service.batch;

import com.application2.demo.module1.service.capitalraidresult.CapitalRaidResultService;
import com.application2.demo.module1.service.clanwar.ClanWarService;
import com.application2.demo.module2.code.ApiCode;
import com.application2.demo.module2.service.ApiEventHistoryService;
import com.application2.demo.module2.service.ApiEventService;
import com.application2.demo.module2.domain.ApiEvent;
import com.application2.demo.module2.domain.ApiEventRepository;
import com.application2.demo.module2.service.dto.ApiEventHistorySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConsumeEventService {
    private final Logger logger =  LoggerFactory.getLogger(ConsumeEventService.class);
    private final ApiEventHistoryService apiEventHistoryService;
    private final ClanWarService clanWarService;
    private final CapitalRaidResultService capitalRaidResultService;
    private final ApiEventService apiEventService;

    private final ApiEventRepository apiEventRepository;
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void consumeEvent() {
        logger.info("ConsumeEvent");
        List<ApiEvent> apiEventList = apiEventService.readApiSchedule();
        long curtime = System.currentTimeMillis();
        for (ApiEvent apiEvent : apiEventList) {
            long eventTime = apiEvent.getEventTime().toInstant(ZoneOffset.UTC).toEpochMilli();
            if (curtime < eventTime) {
                break;
            }
            if (apiEvent.getEventCode() == ApiCode.CLANWAR_SUMMARY) {
                clanWarService.saveClanWar();
                apiEventHistoryService.saveApiEventHistory(ApiEventHistorySaveRequestDto.builder()
                                                            .eventTime(apiEvent.getEventTime())
                                                            .event("clanWarService.saveClanWar()")
                                                            .resultCode(0)
                                                            .regTime(LocalDateTime.now(ZoneOffset.UTC))
                                                            .build());
                apiEventRepository.delete(apiEvent);
            }
            else if (apiEvent.getEventCode() == ApiCode.CAPITALRAID_RESULT) {
                capitalRaidResultService.saveCapitalRaid();
                apiEventHistoryService.saveApiEventHistory(ApiEventHistorySaveRequestDto.builder()
                        .eventTime(apiEvent.getEventTime())
                        .event("capitalRaidResultService.saveCapitalRaid()")
                        .resultCode(0)
                        .regTime(LocalDateTime.now(ZoneOffset.UTC))
                        .build());
                apiEventRepository.delete(apiEvent);
            }
        }
    }
}
