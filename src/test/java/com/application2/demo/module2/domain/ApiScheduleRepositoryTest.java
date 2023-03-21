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
import java.util.Collections;
import java.util.Comparator;
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

    @Test
    public void findOldest() {
        LocalDateTime[] eventTimes = {LocalDateTime.parse("2023-03-21T12:09:00"),
                LocalDateTime.parse("2023-03-21T03:09:00"),
                LocalDateTime.parse("2022-02-19T12:09:00"),
                LocalDateTime.parse("2022-03-18T22:09:00")};
        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);
        for (LocalDateTime ev : eventTimes) {
            apiEventRepository.save(ApiEvent.builder()
                            .eventTime(ev)
                            .eventCode(ApiCode.CLANWAR_SUMMARY)
                            .regTime(regTime)
                            .state("test")
                            .build());

        }
        List<ApiEvent> apiEventList = apiEventRepository.findAll();
        Collections.sort(apiEventList, new Comparator<ApiEvent>() {
            @Override
            public int compare(ApiEvent o1, ApiEvent o2) {
                if (o1.getEventTime().isBefore(o2.getEventTime())) {
                    return -1;
                }
                return 1;
            }
        });
        assertThat(apiEventList.get(0).getEventTime().toEpochSecond(ZoneOffset.UTC)).isEqualTo(eventTimes[2].toEpochSecond(ZoneOffset.UTC));
    }
}