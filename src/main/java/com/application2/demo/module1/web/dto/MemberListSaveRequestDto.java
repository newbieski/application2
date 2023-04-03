package com.application2.demo.module1.web.dto;

import com.application2.demo.module1.domain.memberlist.MemberList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberListSaveRequestDto {
    private String name;
    private String tag;
    private LocalDateTime time;

    @Builder
    public MemberListSaveRequestDto(String name, String tag, LocalDateTime time) {
        this.name = name;
        this.tag = tag;
        this.time = time;
    }

    public MemberList toEntity() {
        return MemberList.builder()
                .name(name)
                .tag(tag)
                .regTime(time)
                .build();
    }
}
