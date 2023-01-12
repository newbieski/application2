package com.application2.demo.domain.clanwar;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class ClanWarAttack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clanwar_id", insertable=false, updatable = false)
    private ClanWarSummary clanWarSummary;

    @Column(name = "clanwar_id")
    private long clanwarId;
    @Column
    private String tag;
    @Column
    private String name;
    @Column
    private long townhallLevel;
    @Column
    private long mapPosition;
    @Column
    private String defenderTag;
    @Column
    private long stars;
    @Column
    private double destructionPercentage;
    @Column
    LocalDateTime regTime;

    @Builder
    public ClanWarAttack(ClanWarSummary clanWarSummary,
                         Long clanwarId,
                         String tag,
                         String name,
                         long townhallLevel,
                         long mapPosition,
                         String defenderTag,
                         long stars,
                         double destructionPercentage,
                         LocalDateTime regTime) {
        this.clanwarId = clanwarId;
        this.tag = tag;
        this.name = name;
        this.townhallLevel = townhallLevel;
        this.mapPosition = mapPosition;
        this.defenderTag = defenderTag;
        this.stars = stars;
        this.destructionPercentage = destructionPercentage;
        this.regTime = regTime;
    }
}
