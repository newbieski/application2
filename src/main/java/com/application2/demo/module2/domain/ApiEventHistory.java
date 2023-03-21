package com.application2.demo.module2.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@NoArgsConstructor
@Entity
public class ApiEventHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime eventTime;

    @Column
    private String event;

    @Column
    private LocalDateTime regTime;

    @Builder
    public ApiEventHistory(LocalDateTime eventTime,
                    String event,
                    LocalDateTime regTime) {
        this.eventTime = eventTime;
        this.event = event;
        this.regTime = regTime;
    }
}
