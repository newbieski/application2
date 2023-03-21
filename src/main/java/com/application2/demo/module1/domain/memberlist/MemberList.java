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
    private LocalDateTime regTime;

    @Builder
    public MemberList(String name, String tag, LocalDateTime time) {
        this.name = name;
        this.tag = tag;
        this.regTime = time;
    }
}
