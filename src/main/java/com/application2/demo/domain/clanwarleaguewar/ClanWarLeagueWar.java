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
public class ClanWarLeagueWar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String warTag;

    @Column
    private String state;
    
    @Column
    private Long teamSize;
    
    @Column
    private LocalDateTime startTime;
    
    @Column
    private LocalDateTime endTime;
    
    @Column
    private String clanTag;
    
    @Column
    private String clanName;
    
    @Column
    private String opponentTag;
    
    @Column
    private String opponentName;

    @Column
    private LocalDateTime regTime;

    @Builder
    public ClanWarLeagueWar(String warTag,
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
}
