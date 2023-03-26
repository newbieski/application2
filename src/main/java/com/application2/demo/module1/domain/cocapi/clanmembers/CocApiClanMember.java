package com.application2.demo.module1.domain.cocapi.clanmembers;

import com.application2.demo.module1.domain.memberlist.MemberList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CocApiClanMember {
    private String name;
    private String tag;
    private String role;
    private Long expLevel;
    private Long donations;
    private Long donationsReceived;
    private Long trophies;
    private LocalDateTime regTime;

    @Builder
    public CocApiClanMember(String name,
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

    public MemberList toEntity() {
        return MemberList.builder()
                .name(name)
                .tag(tag)
                .role(role)
                .expLevel(expLevel)
                .donations(donations)
                .donationsReceived(donationsReceived)
                .trophies(trophies)
                .regTime(regTime)
                .build();
    }
}
