package com.application2.demo.domain.CapitalRaidResult;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class CapitalRaidResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String tag;

    @Column
    private Long attackCount;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private LocalDateTime regTime;

    @Builder
    public CapitalRaidResult(String name,
                             String tag,
                             Long attackCount,
                             LocalDateTime startTime,
                             LocalDateTime endTime,
                             LocalDateTime regTime) {
        this.name = name;
        this.tag = tag;
        this.attackCount = attackCount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.regTime = regTime;
    }

}
