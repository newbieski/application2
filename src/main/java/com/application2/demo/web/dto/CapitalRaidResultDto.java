package com.application2.demo.web.dto;

import com.application2.demo.domain.capitalraidresult.CapitalRaidResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CapitalRaidResultDto {
    private String name;
    private String tag;
    private Long attackCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime regTime;

    @Builder
    CapitalRaidResultDto(String name,
                         String tag,
                         Long attackCount,
                         LocalDateTime startTime,
                         LocalDateTime endTime,
                         LocalDateTime regTime) {
        this.name = name;
        this.tag = tag;
        this.attackCount = attackCount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.regTime = regTime;
    }

    public CapitalRaidResult toEntity() {
        return CapitalRaidResult.builder()
                .name(name)
                .tag(tag)
                .attackCount(attackCount)
                .startTime(startTime)
                .endTime(endTime)
                .regTime(regTime)
                .build();
    }
}
