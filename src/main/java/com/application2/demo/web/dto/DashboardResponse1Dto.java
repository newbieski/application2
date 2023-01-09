package com.application2.demo.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class DashboardResponse1Dto {
    private String name;
    private String tag;
    private long capitalRaidAttackCount;
    private long clanwarlegueTotalAttackCount;
    private long clanwarlegueUsedAttackCount;
    private long clanwarleagueStars;
    
    @Builder
    public DashboardResponse1Dto(String name,
                                String tag,
                                long capitalRaidAttackCount,
                                long clanwarlegueTotalAttackCount,
                                long clanwarlegueUsedAttackCount,
                                long clanwarleagueStars) {
        this.name = name;
        this.tag = tag;
        this.capitalRaidAttackCount = capitalRaidAttackCount;
        this.clanwarlegueTotalAttackCount = clanwarlegueTotalAttackCount;
        this.clanwarlegueUsedAttackCount = clanwarlegueUsedAttackCount;
        this.clanwarleagueStars = clanwarleagueStars;
    }
}