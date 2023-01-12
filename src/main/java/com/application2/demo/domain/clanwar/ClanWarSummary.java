package com.application2.demo.domain.clanwar;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class ClanWarSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String clanTag;
    @Column
    private String clanName;
    @Column
    private long clanAttacks;
    @Column
    private long clanStars;
    @Column
    private double clanDestructionPercentage;
    @Column
    private String opponentTag;
    @Column
    private String opponentName;
    @Column
    private long opponentAttacks;
    @Column
    private long opponentStars;
    @Column
    private double opponentDestructionPercentage;

    @Column
    private String state;
    @Column
    LocalDateTime startTime;
    @Column
    LocalDateTime endTime;
    @Column
    LocalDateTime regTime;

    @Builder
    public ClanWarSummary(String clanTag,
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
}
