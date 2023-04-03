package com.application2.demo.module2.service.batch;

import com.application2.demo.module1.service.memberlist.MemberListService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClanMembersBatchService {
    private final Logger logger = LoggerFactory.getLogger(ClanMembersBatchService.class);
    private final MemberListService memberListService;

    @Scheduled(cron = "0 10 0 1/1 * *")
    public void getMemberListService() {
        logger.info("PollingClanMembers");
        memberListService.saveClanMembers();
    }
}
