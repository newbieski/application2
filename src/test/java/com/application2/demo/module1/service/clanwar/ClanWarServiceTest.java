package com.application2.demo.module1.service.clanwar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ClanWarServiceTest {
    @Autowired
    ClanWarService clanWarService;
    @Test
    void subtract_min() {
        LocalDateTime eventTime = LocalDateTime.parse("2023-03-21T11:01:00");
        LocalDateTime result = clanWarService.subtract_min(eventTime,5);
        eventTime = eventTime.minusMinutes(5);
        assertThat(eventTime.toInstant(ZoneOffset.UTC).toEpochMilli()).isEqualTo(result.toInstant(ZoneOffset.UTC).toEpochMilli());
    }
}