package com.application2.demo.module2.domain;

import com.application2.demo.module2.code.ApiCode;
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

    @Transient
    private String state;

    @Builder
    public ApiEvent(LocalDateTime eventTime,
                    Long eventCode,
                    LocalDateTime regTime,
                    String state) {
        this.eventTime = eventTime;
        this.eventCode = eventCode;
        this.regTime = regTime;
        this.state = state;
    }

    public boolean isValid() {
        if (!isValidState()) {
            return false;
        }
        long current_ms = System.currentTimeMillis();
        long event_ms = eventTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        long twodays_ms = 2 * 24 * 60 * 60 * 1000;

        return current_ms - twodays_ms <= event_ms && event_ms <= current_ms + twodays_ms;
    }

    private boolean isValidState() {
        if (eventCode == ApiCode.CLANWAR_SUMMARY) {
            String[] validState = {"preparation", "war", "inWar", "warEnded"};
            for (String target : validState) {
                if (target.equals(state)) {
                    return true;
                }
            }
            return false;
        }
        else if (eventCode == ApiCode.CAPITALRAID_RESULT) {
            String[] validState = {"ended"};
            return true;
        }
        return false;
    }
}
