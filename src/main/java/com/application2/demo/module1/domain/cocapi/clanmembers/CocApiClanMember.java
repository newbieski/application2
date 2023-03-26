package com.application2.demo.module1.domain.cocapi.clanmembers;

import com.application2.demo.module1.domain.memberlist.MemberList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CocApiClanMember {
    private String name;
    private String tag;
    private LocalDateTime regTime;

    @Builder
    public CocApiClanMember(String name, String tag, LocalDateTime regTime) {
        this.name = name;
        this.tag = tag;
        this.regTime = regTime;
    }

    public MemberList toEntity() {
        return MemberList.builder()
                .name(name)
                .tag(tag)
                .regTime(regTime)
                .build();
    }
}
