package com.application2.demo.domain.warleaguetaglist;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class WarLeagueTagList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tag;
    
    @Column
    private LocalDateTime warLeagueTime;

    @Column
    private LocalDateTime regTime;

    @Builder
    public WarLeagueTagList(String tag, LocalDateTime warLeagueTime, LocalDateTime regTime) {
        this.tag = tag;
        this.warLeagueTime = warLeagueTime;
        this.regTime = regTime;
    }
}
