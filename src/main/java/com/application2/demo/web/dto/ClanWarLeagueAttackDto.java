package com.application2.demo.web.dto;

import com.application2.demo.domain.clanwarleagueattack.ClanWarLeagueAttack;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ClanWarLeagueAttackDto {
    private String warTag;
    private String opponent;
    private String attackerTag;
    private String attackerName;
    private Long attackerMapPosition;
    private String defenderTag;
    private Long stars;
    private double destructionPercentage;
    private LocalDateTime regTime;

    @Builder
    public ClanWarLeagueAttackDto(String warTag,
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

    public ClanWarLeagueAttack toEntity() {
        return ClanWarLeagueAttack.builder()
                .warTag(warTag)
                .opponent(opponent)
                .attackerTag(attackerTag)
                .attackerName(attackerName)
                .attackerMapPosition(attackerMapPosition)
                .defenderTag(defenderTag)
                .stars(stars)
                .destructionPercentage(destructionPercentage)
                .regTime(regTime)
                .build();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        return sb.append(warTag).append(" ")
                .append(opponent).append(" ")
                .append(attackerTag).append(" ")
                .append(attackerName).append(" ")
                .append(attackerMapPosition).append(" ")
                .append(defenderTag).append(" ")
                .append(stars).append(" ")
                .append(destructionPercentage).append(" ")
                .append(regTime)
                .toString();
    }
}
