package com.application2.demo.module1.domain.cocapi.capitalraid;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CocApiCapitalRaidSummary {
    private String state;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    public CocApiCapitalRaidSummary(String state,
                                    LocalDateTime startTime,
                                    LocalDateTime endTime) {
        this.state = state;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
