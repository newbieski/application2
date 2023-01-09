package com.application2.demo.web.dto;

import com.application2.demo.domain.memberlist.MemberList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberListResponseDto {
    private String name;
    private String tag;
    private LocalDateTime regTime;
    
    @Builder
    public MemberListResponseDto(String name, String tag, LocalDateTime regTime) {
        this.name = name;
        this.tag = tag;
        this.regTime = regTime;
    }
}
