package com.application2.demo.web.dto;

import com.application2.demo.domain.clanwarleaguewar.ClanWarLeagueWarClanSummary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

public class ClanWarLeagueWarClanSummaryDto {
    private String warTag;
    private String clanTag;
    private String clanName;
    private long clanLevel;
    private long attacks;
    private long stars;
    private LocalDateTime regTime;
    
    @Builder
    public ClanWarLeagueWarClanSummaryDto(String warTag,
                                    String clanTag,
                                    String clanName,
                                    long clanLevel,
                                    long attacks,
                                    long stars,
                                    LocalDateTime regTime) {
        this.warTag = warTag;
        this.clanTag = clanTag;
        this.clanName = clanName;
        this.clanLevel = clanLevel;
        this.attacks = attacks;
        this.stars = stars;
        this.regTime = regTime;
    }
    
    public ClanWarLeagueWarClanSummary toEntity() {
        return ClanWarLeagueWarClanSummary.builder()
                .warTag(warTag)
                .clanTag(clanTag)
                .clanName(clanName)
                .clanLevel(clanLevel)
                .attacks(attacks)
                .stars(stars)
                .regTime(regTime)
                .build();
    }
}