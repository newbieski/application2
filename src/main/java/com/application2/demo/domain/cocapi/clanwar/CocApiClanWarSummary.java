package com.application2.demo.domain.cocapi.clanwar;

import com.application2.demo.domain.clanwar.ClanWarSummary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CocApiClanWarSummary {
    private String clanTag;
    private String clanName;
    private long clanAttacks;
    private long clanStars;
    private double clanDestructionPercentage;
    private String opponentTag;
    private String opponentName;
    private long opponentAttacks;
    private long opponentStars;
    private double opponentDestructionPercentage;

    private String state;
    LocalDateTime startTime;
    LocalDateTime endTime;
    LocalDateTime regTime;

    @Builder
    public CocApiClanWarSummary(String clanTag,
                                String clanName,
                                long clanAttacks,
                                long clanStars,
                                double clanDestructionPercentage,
                                String opponentTag,
                                String opponentName,
                                long opponentAttacks,
                                long opponentStars,
                                double opponentDestructionPercentage,
                                String state,
                                LocalDateTime startTime,
                                LocalDateTime endTime,
                                LocalDateTime regTime) {
        this.clanTag = clanTag;
        this.clanName = clanName;
        this.clanAttacks = clanAttacks;
        this.clanStars = clanStars;
        this.clanDestructionPercentage = clanDestructionPercentage;
        this.opponentTag = opponentTag;
        this.opponentName = opponentName;
        this.opponentAttacks = opponentAttacks;
        this.opponentStars = opponentStars;
        this.opponentDestructionPercentage = opponentDestructionPercentage;
        this.state = state;
        this.startTime = startTime;
        this.endTime = endTime;
        this.regTime = regTime;
    }

    public ClanWarSummary toEntity() {
        return ClanWarSummary.builder()
                .clanTag(clanTag)
                .clanName(clanName)
                .clanAttacks(clanAttacks)
                .clanStars(clanStars)
                .clanDestructionPercentage(clanDestructionPercentage)
                .opponentTag(opponentTag)
                .opponentName(opponentName)
                .opponentAttacks(opponentAttacks)
                .opponentStars(opponentStars)
                .opponentDestructionPercentage(opponentDestructionPercentage)
                .state(state)
                .startTime(startTime)
                .endTime(endTime)
                .regTime(regTime)
                .build();
    }
}
