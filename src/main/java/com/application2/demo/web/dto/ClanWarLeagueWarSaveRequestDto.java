package com.application2.demo.web.dto;

import com.application2.demo.domain.clanwarleaguewar.ClanWarLeagueWar;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

public class ClanWarLeagueWarSaveRequestDto {
    
    private String warTag;
    private String state;
    private Long teamSize;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String clanTag;
    private String clanName;
    private String opponentTag;
    private String opponentName;
    private LocalDateTime regTime;
    
    @Builder
    public ClanWarLeagueWarSaveRequestDto(String warTag,
                                          String state,
                                          Long teamSize,
                                          LocalDateTime startTime,
                                          LocalDateTime endTime,
                                          String clanTag,
                                          String clanName,
                                          String opponentTag,
                                          String opponentName,
                                          LocalDateTime regTime) {
        this.warTag = warTag;
        this.state = state;
        this.teamSize = teamSize;
        this.startTime = startTime;
        this.endTime = endTime;
        this.clanTag = clanTag;
        this.clanName = clanName;
        this.opponentTag = opponentTag;
        this.opponentName = opponentName;
        this.regTime = regTime;
    }
    
    public ClanWarLeagueWar toEntity() {
        return ClanWarLeagueWar.builder()
                .warTag(warTag)
                .state(state)
                .teamSize(teamSize)
                .startTime(startTime)
                .endTime(endTime)
                .clanTag(clanTag)
                .clanName(clanName)
                .opponentTag(opponentTag)
                .opponentName(opponentName)
                .regTime(regTime)
                .build();
    }
}