package com.application2.demo.module2.domain;

import com.application2.demo.module2.code.ApiCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApiScheduleRepositoryTest {
    @Autowired
    ApiEventRepository apiEventRepository;

    @AfterEach
    public void cleanup() {
        apiEventRepository.deleteAll();
    }

    @Test
    public void save_read() {
        LocalDateTime eventTime = LocalDateTime.parse("2023-03-21T08:37:00");
        long eventCode = 99;
        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);
        ApiEvent myEvent= ApiEvent.builder()
                .eventTime(eventTime)
                .eventCode(eventCode)
                .regTime(regTime)
                .build();
        apiEventRepository.save(myEvent);
        List<ApiEvent> apiEventList = apiEventRepository.findAll();
        ApiEvent apiEvent = apiEventList.get(0);
        System.out.println(apiEvent.getEventTime());
        System.out.println(apiEvent.getEventCode());
        System.out.println(apiEvent.getRegTime());
        assertThat(apiEvent.getEventTime().toEpochSecond(ZoneOffset.UTC)).isEqualTo(eventTime.toEpochSecond(ZoneOffset.UTC));
        assertThat(apiEvent.getEventCode()).isEqualTo(eventCode);
        assertThat(apiEvent.getRegTime().toEpochSecond(ZoneOffset.UTC)).isEqualTo(regTime.toEpochSecond(ZoneOffset.UTC));
    }

    @Test
    public void findby() {
        LocalDateTime eventTime = LocalDateTime.parse("2023-03-21T12:09:00");
        long eventCode = ApiCode.CLANWAR_SUMMARY;
        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);
        List<ApiEvent> res = apiEventRepository.findAllByEventTimeAndEventCode(eventTime, eventCode);
        assertThat(res.isEmpty()).isEqualTo(true);
        ApiEvent myEvent = ApiEvent.builder()
                .eventTime(eventTime)
                .eventCode(eventCode)
                .regTime(regTime)
                .build();
        apiEventRepository.save(myEvent);
        res = apiEventRepository.findAllByEventTimeAndEventCode(eventTime, eventCode);
        assertThat(res.isEmpty()).isNotEqualTo(true);
        assertThat(res.get(0).getEventTime().toEpochSecond(ZoneOffset.UTC)).isEqualTo(eventTime.toEpochSecond(ZoneOffset.UTC));
        assertThat(res.get(0).getEventCode()).isEqualTo(eventCode);
    }
}