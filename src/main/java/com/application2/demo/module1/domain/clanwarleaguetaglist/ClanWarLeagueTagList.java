package com.application2.demo.module1.domain.clanwarleaguetaglist;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class ClanWarLeagueTagList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tag;
    
    @Column
    private LocalDateTime clanWarLeagueTime;

    @Column
    private LocalDateTime regTime;

    @Builder
    public ClanWarLeagueTagList(String tag, LocalDateTime clanWarLeagueTime, LocalDateTime regTime) {
        this.tag = tag;
        this.clanWarLeagueTime = clanWarLeagueTime;
        this.regTime = regTime;
    }
}
