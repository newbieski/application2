package com.application2.demo.module1.web.dto;

import com.application2.demo.module1.domain.clanwarleaguetaglist.ClanWarLeagueTagList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ClanWarLeagueTagListSaveRequestDto {
    private String tag;
    private LocalDateTime clanWarLeagueTime;
    private LocalDateTime regTime;
    
    @Builder
    public ClanWarLeagueTagListSaveRequestDto(String tag, LocalDateTime clanWarLeagueTime, LocalDateTime regTime) {
        this.tag = tag;
        this.clanWarLeagueTime = clanWarLeagueTime;
        this.regTime = regTime;
    }
    
    public ClanWarLeagueTagList toEntity() {
        return ClanWarLeagueTagList.builder()
                .tag(tag)
                .clanWarLeagueTime(clanWarLeagueTime)
                .regTime(regTime)
                .build();
    }
}