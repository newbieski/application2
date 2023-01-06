package com.application2.demo.web.dto;

import com.application2.demo.domain.clanwarleaguetaglist.ClanWarLeagueTagList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ClanWarLeagueTagListDto {
    private String tag;
    private LocalDateTime clanWarLeagueTime;
    private LocalDateTime regTime;
    
    @Builder
    public ClanWarLeagueTagListDto(String tag, LocalDateTime clanWarLeagueTime, LocalDateTime regTime) {
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