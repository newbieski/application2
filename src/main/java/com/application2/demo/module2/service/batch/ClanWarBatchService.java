package com.application2.demo.module2.service.batch;

import com.application2.demo.module1.service.clanwar.ClanWarService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClanWarBatchService {
    Logger logger = LoggerFactory.getLogger(ClanWarBatchService.class);
    private final ClanWarService clanWarService;
    @Scheduled(cron = "0 0 0/12 * * *")
    public void PollingClanWar() {
        logger.info("PollingClanWar");
        clanWarService.saveClanWar();
    }
}
