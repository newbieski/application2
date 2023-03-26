package com.application2.demo.module2.service.dto;

import com.application2.demo.module2.domain.ApiEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ApiEventSaveRequestDto {
    LocalDateTime eventTime;
    long eventCode;
    String state;

    LocalDateTime regTime;

    @Builder
    public ApiEventSaveRequestDto(LocalDateTime eventTime,
                                  long eventCode,
                                  String state,
                                  LocalDateTime regTime) {
        this.eventTime = eventTime;
        this.eventCode = eventCode;
        this.state = state;
        this.regTime = regTime;
    }

    public ApiEvent toEntity() {
        return ApiEvent.builder()
                .eventTime(this.eventTime)
                .eventCode(this.eventCode)
                .state(this.state)
                .regTime(this.regTime)
                .build();
    }
}
