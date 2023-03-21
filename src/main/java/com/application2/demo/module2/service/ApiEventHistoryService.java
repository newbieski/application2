package com.application2.demo.module2.service;

import com.application2.demo.module2.domain.ApiEventHistory;
import com.application2.demo.module2.domain.ApiEventHistoryRepository;
import com.application2.demo.module2.service.dto.ApiEventHistorySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApiEventHistoryService {
    private Logger logger =  LoggerFactory.getLogger(ApiEventHistoryService.class);
    private final ApiEventHistoryRepository apiEventHistoryRepository;
    public void saveApiEventHistory(ApiEventHistorySaveRequestDto dto) {
        ApiEventHistory apiEventHistory = dto.toEntity();
        apiEventHistoryRepository.save(apiEventHistory);
    }
}
