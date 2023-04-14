package com.application2.demo.module1.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
public class DashboardResponse1Dto {
    private String name;
    private String tag;
    private long capitalRaidAttackCount;
    private long clanwarlegueTotalAttackCount;
    private long clanwarlegueUsedAttackCount;
    private long clanwarleagueStars;
    private long clanwarTotalAttackCount;
    private long clanwarUsedAttackCount;
    private long clanwarStars;
    private String role;
    private long donations;
    private long donationsReceived;

    private long memberPoint;

    @Builder
    public DashboardResponse1Dto(String name,
                                 String tag,
                                 long capitalRaidAttackCount,
                                 long clanwarlegueTotalAttackCount,
                                 long clanwarlegueUsedAttackCount,
                                 long clanwarleagueStars,
                                 long clanwarTotalAttackCount,
                                 long clanwarUsedAttackCount,
                                 long clanwarStars,
                                 String role,
                                 long donations,
                                 long donationsReceived) {
        this.name = name;
        this.tag = tag;
        this.capitalRaidAttackCount = capitalRaidAttackCount;
        this.clanwarlegueTotalAttackCount = clanwarlegueTotalAttackCount;
        this.clanwarlegueUsedAttackCount = clanwarlegueUsedAttackCount;
        this.clanwarleagueStars = clanwarleagueStars;
        this.clanwarTotalAttackCount = clanwarTotalAttackCount;
        this.clanwarUsedAttackCount = clanwarUsedAttackCount;
        this.clanwarStars = clanwarStars;
        this.role = role;
        this.donations = donations;
        this.donationsReceived = donationsReceived;
        this.memberPoint = calcMemberPoint();
    }

    private long calcMemberPoint() {
        // clanwar, clanleague, capital, donation, role
        long[] weight = {100, 100, 2, 3, 1};
        long[] point = new long[5];

        // 참 유지보수 힘들게 구현한다
        point[0] = getClanWarPoint();
        point[1] = getClanLeaguePoint();
        point[2] = getCapitalPoint();
        point[3] = getDonationPoint();
        point[4] = getRolePoint();
        long res = 0;
        for (int i = 0 ; i < 5 ; i++) {
            res += weight[i] * point[i];
        }
        return res;
    }

    private long getClanWarPoint() {
        if (clanwarTotalAttackCount > 0) {
            if (clanwarUsedAttackCount == 0) {
                return -clanwarTotalAttackCount * 100;
            }
            return clanwarUsedAttackCount * 100 / clanwarTotalAttackCount;
        }
        return 0;
    }

    private long getClanLeaguePoint() {
        if (clanwarlegueTotalAttackCount > 0) {
            if (clanwarlegueUsedAttackCount == 0) {
                return -clanwarlegueTotalAttackCount * 100;
            }
            return clanwarlegueUsedAttackCount * 100 / clanwarlegueTotalAttackCount;
        }
        return 0;
    }

    private long getCapitalPoint() {
        return capitalRaidAttackCount;
    }

    private long getDonationPoint() {
        return donations * 10 + donationsReceived;
    }

    private long getRolePoint() {
        if (role.equals("member")) {
            return 0;
        }
        return 1;
    }
}