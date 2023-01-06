package com.application2.demo.web.dto;

import com.application2.demo.domain.warleaguetaglist.WarLeagueTagList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class WarLeagueTagListDto {
    private String tag;
    private LocalDateTime warLeagueTime;
    private LocalDateTime regTime;
    
    @Builder
    public WarLeagueTagListDto(String tag, LocalDateTime warLeagueTime, LocalDateTime regTime) {
        this.tag = tag;
        this.warLeagueTime = warLeagueTime;
        this.regTime = regTime;
    }
    
    public WarLeagueTagList toEntity() {
        return WarLeagueTagList.builder()
                .tag(tag)
                .warLeagueTime(warLeagueTime)
                .regTime(regTime)
                .build();
    }
}