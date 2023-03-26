package com.application2.demo.module1.domain.cocapi.repository;

import com.application2.demo.module1.config.ClanConfig;
import com.application2.demo.module1.domain.cocapi.clanmembers.CocApiClanMember;
import com.application2.demo.module1.web.dto.MemberListSaveRequestDto;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class CocClanMembers {
    @Autowired
    CocHttpRequest cocHttpRequest;
    @Autowired
    ClanConfig clanConfig;

    private String url;
    private JSONObject source;

    public void load() {
        url = "/clans"+"/"+ clanConfig.getCLAN_TAG()+"/members";
        source = cocHttpRequest.getRequest(url);
    }

    public String raw() {
        return source.toString();
    }

    public List<CocApiClanMember> getClanMembers() {
        List<CocApiClanMember> res = new ArrayList<CocApiClanMember>();

        JSONArray items = source.getJSONArray("items");

        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);
        for (int i = 0 ; i < items.length() ; i++) {
            JSONObject member = items.getJSONObject(i);
            CocApiClanMember cocApiClanMember = CocApiClanMember.builder()
                    .name(member.getString("name"))
                    .tag(member.getString("tag"))
                    .role(member.getString("role"))
                    .expLevel(member.getLong("expLevel"))
                    .donations(member.getLong("donations"))
                    .donationsReceived(member.getLong("donationsReceived"))
                    .trophies(member.getLong("trophies"))
                    .regTime(regTime)
                    .build();
            res.add(cocApiClanMember);
        }
        return res;
    }
}
