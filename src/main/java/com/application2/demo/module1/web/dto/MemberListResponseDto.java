package com.application2.demo.module1.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberListResponseDto {
    private String name;
    private String tag;
    private String role;
    private Long donations;
    private Long donationsReceived;
    private LocalDateTime regTime;
    
    @Builder
    public MemberListResponseDto(String name,
                                 String tag,
                                 String role,
                                 Long donations,
                                 Long donationsReceived,
                                 LocalDateTime regTime) {
        this.name = name;
        this.tag = tag;
        this.role = role;
        this.donations = donations;
        this.donationsReceived = donationsReceived;
        this.regTime = regTime;
    }
}
