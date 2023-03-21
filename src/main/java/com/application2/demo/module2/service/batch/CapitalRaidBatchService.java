package com.application2.demo.module2.service.batch;

import com.application2.demo.module1.service.capitalraidresult.CapitalRaidResultService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CapitalRaidBatchService {
    private final Logger logger = LoggerFactory.getLogger(CapitalRaidBatchService.class);
    private final CapitalRaidResultService capitalRaidResultService;
    @Scheduled(cron = "0 5 0 1/1 * *")
    public void PollingCapitalRaid() {
        logger.info("PollingCapitalRaid");
        capitalRaidResultService.saveCapitalRaid();
    }
}
