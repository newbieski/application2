package com.application2.demo.module1.domain.cocapi.clanwar;

import com.application2.demo.module1.domain.clanwar.ClanWarAttack;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CocApiClanWarAttack {
    private String tag;
    private String name;
    private long townhallLevel;
    private long mapPosition;
    private String defenderTag;
    private long stars;
    private double destructionPercentage;
    private LocalDateTime regTime;

    @Builder
    public CocApiClanWarAttack(String tag,
                               String name,
                               long townhallLevel,
                               long mapPosition,
                               String defenderTag,
                               long stars,
                               double destructionPercentage,
                               LocalDateTime regTime) {
        this.tag = tag;
        this.name = name;
        this.townhallLevel = townhallLevel;
        this.mapPosition = mapPosition;
        this.defenderTag = defenderTag;
        this.stars = stars;
        this.destructionPercentage = destructionPercentage;
        this.regTime = regTime;
    }

    public ClanWarAttack toEntity(long clanwarId) {
        return ClanWarAttack.builder()
                .clanwarId(clanwarId)
                .tag(tag)
                .name(name)
                .townhallLevel(townhallLevel)
                .mapPosition(mapPosition)
                .defenderTag(defenderTag)
                .stars(stars)
                .destructionPercentage(destructionPercentage)
                .regTime(regTime)
                .build();
    }
}
