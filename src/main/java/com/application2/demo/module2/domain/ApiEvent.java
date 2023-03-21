package com.application2.demo.module2.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class ApiEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime eventTime;

    @Column
    private long eventCode;

    @Column
    private LocalDateTime regTime;

    @Builder
    public ApiEvent(LocalDateTime eventTime,
                    Long eventCode,
                    LocalDateTime regTime) {
        this.eventTime = eventTime;
        this.eventCode = eventCode;
        this.regTime = regTime;
    }
}
