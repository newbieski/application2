package com.application2.demo.module2.service;

import com.application2.demo.module1.service.clanwar.ClanWarService;
import com.application2.demo.module2.code.ApiCode;
import com.application2.demo.module2.domain.ApiEvent;
import com.application2.demo.module2.domain.ApiEventRepository;
import com.application2.demo.module2.service.dto.ApiEventHistorySaveRequestDto;
import com.application2.demo.module2.service.dto.ApiEventSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ApiEventService {
    private Logger logger = LoggerFactory.getLogger(ApiEventService.class);
    private final ApiEventRepository apiEventRepository;

    public void saveApiEvent(ApiEventSaveRequestDto dto) {
        ApiEvent apiEvent = dto.toEntity();
        if (apiEvent.isValid() && apiEventRepository.findAllByEventTimeAndEventCode(dto.getEventTime(), dto.getEventCode()).isEmpty()) {
            apiEventRepository.save(apiEvent);
        }
    }
    public List<ApiEvent> readApiSchedule() {
        return readSorted();
    }

    private List<ApiEvent> readSorted() {
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
        return apiEventList;
    }
}
