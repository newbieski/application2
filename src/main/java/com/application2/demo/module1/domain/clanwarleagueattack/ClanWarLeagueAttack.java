package com.application2.demo.module1.domain.clanwarleagueattack;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class ClanWarLeagueAttack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String warTag;

    @Column
    private String opponent;

    @Column
    private String attackerTag;

    @Column
    private String attackerName;

    @Column
    private Long attackerMapPosition;

    @Column
    private String defenderTag;

    @Column
    private Long stars;

    @Column
    private double destructionPercentage;

    @Column
    private LocalDateTime regTime;

    @Builder
    public ClanWarLeagueAttack(String warTag,
                               String opponent,
                               String attackerTag,
                               String attackerName,
                               Long attackerMapPosition,
                               String defenderTag,
                               Long stars,
                               double destructionPercentage,
                               LocalDateTime regTime) {
        this.warTag = warTag;
        this.opponent = opponent;
        this.attackerTag = attackerTag;
        this.attackerName = attackerName;
        this.attackerMapPosition = attackerMapPosition;
        this.defenderTag = defenderTag;
        this.stars = stars;
        this.destructionPercentage = destructionPercentage;
        this.regTime = regTime;
    }

}
