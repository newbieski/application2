package com.application2.demo.module2.service.dto;

import com.application2.demo.module2.domain.ApiEventHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ApiEventHistorySaveRequestDto {
    private LocalDateTime eventTime;
    private String event;
    private long resultCode;
    private LocalDateTime regTime;
    @Builder
    public ApiEventHistorySaveRequestDto(LocalDateTime eventTime,
                                         String event,
                                         long resultCode,
                                         LocalDateTime regTime) {
        this.eventTime = eventTime;
        this.event = event;
        this.resultCode = resultCode;
        this.regTime = regTime;
    }

    public ApiEventHistory toEntity() {
        return ApiEventHistory.builder()
                .eventTime(eventTime)
                .event(event)
                .resultCode(resultCode)
                .regTime(regTime)
                .build();
    }
}
