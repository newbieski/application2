package com.application2.demo.module1.domain.cocapi.repository;

import com.application2.demo.module1.config.ClanConfig;
import com.application2.demo.module1.domain.cocapi.capitalraid.CocApiCapitalRaid;
import com.application2.demo.module1.domain.cocapi.capitalraid.CocApiCapitalRaidSummary;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class CocCapitalRaid {
    @Autowired
    CocHttpRequest cocHttpRequest;
    @Autowired
    ClanConfig clanConfig;

    private String url;
    private JSONObject source;

    public void load() {
        url = "/clans"+"/"+ clanConfig.getCLAN_TAG()+"/capitalraidseasons";
        source = cocHttpRequest.getRequest(url);
    }

    public String raw() {
        return source.toString();
    }

    public CocApiCapitalRaidSummary getCapitalRaidSummary() {
        JSONObject item = source.getJSONArray("items").getJSONObject(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSS'Z'");
        return CocApiCapitalRaidSummary.builder()
                .state(item.get("state").toString())
                .startTime(LocalDateTime.parse(item.get("startTime").toString(), formatter))
                .endTime(LocalDateTime.parse(item.get("endTime").toString(), formatter))
                .build();
    }

    public List<CocApiCapitalRaid> getCapitalRaid() {
        List<CocApiCapitalRaid> res = new ArrayList<CocApiCapitalRaid>();

        JSONArray items = source.getJSONArray("items");
        JSONArray members = items.getJSONObject(0).getJSONArray("members");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSS'Z'");
        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime startTime = LocalDateTime.parse(items.getJSONObject(0).get("startTime").toString(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(items.getJSONObject(0).get("endTime").toString(), formatter);
        for (int i = 0 ; i < members.length() ; i++) {
            JSONObject member = members.getJSONObject(i);
            CocApiCapitalRaid cocApiCapitalRaid = CocApiCapitalRaid.builder()
                    .name(member.get("name").toString())
                    .tag(member.get("tag").toString())
                    .attackCount(Long.valueOf(member.get("attacks").toString()))
                    .startTime(startTime)
                    .endTime(endTime)
                    .regTime(regTime)
                    .build();
            res.add(cocApiCapitalRaid);
        }

        return res;
    }
}
