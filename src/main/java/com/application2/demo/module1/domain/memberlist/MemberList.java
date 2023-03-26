package com.application2.demo.module1.domain.memberlist;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class MemberList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String tag;

    @Column
    private String role;

    @Column
    private Long expLevel;

    @Column
    private Long donations;

    @Column
    private Long donationsReceived;

    @Column
    private Long trophies;

    @Column
    private LocalDateTime regTime;

    @Builder
    public MemberList(String name,
                      String tag,
                      String role,
                      Long expLevel,
                      Long donations,
                      Long donationsReceived,
                      Long trophies,
                      LocalDateTime regTime) {
        this.name = name;
        this.tag = tag;
        this.role = role;
        this.expLevel = expLevel;
        this.donations = donations;
        this.donationsReceived = donationsReceived;
        this.trophies = trophies;
        this.regTime = regTime;
    }
}
