package com.application2.demo.domain.memberlist;

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

    @Temporal(TemporalType.TIMESTAMP)
    Date regTimestamp;

    @Column
    private String name;

    @Column
    private String tag;

    @Column
    private LocalDateTime time;

    @Builder
    public MemberList(String name, String tag, LocalDateTime time) {
        this.name = name;
        this.tag = tag;
        this.time = time;
    }
}
