package com.application2.demo.domain.clanwarleaguewar;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class ClanWarLeagueWarClanSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String warTag;
    
    @Column
    private String clanTag;
    
    @Column
    private String clanName;
    
    @Column
    private long clanLevel;
    
    @Column
    private long attacks;
    
    @Column
    private long stars;
    
    @Column
    private LocalDateTime regTime;
    
    @Builder
    public ClanWarLeagueWarClanSummary(String warTag,
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
    
}