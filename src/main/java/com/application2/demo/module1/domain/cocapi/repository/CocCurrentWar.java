package com.application2.demo.module1.domain.cocapi.repository;

import com.application2.demo.module1.config.ClanConfig;
import com.application2.demo.module1.domain.cocapi.clanwar.CocApiClanWarAttack;
import com.application2.demo.module1.domain.cocapi.clanwar.CocApiClanWarSummary;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Component
@NoArgsConstructor
public class CocCurrentWar {
    private Logger logger = LoggerFactory.getLogger(CocCurrentWar.class);
    @Autowired
    CocHttpRequest cocHttpRequest;
    @Autowired
    ClanConfig clanConfig;
    private String url;
    private JSONObject source;

    public void load() {
        url = "/clans"+"/"+ clanConfig.getCLAN_TAG()+"/currentwar";
        source = cocHttpRequest.getRequest(url);
    }

    public String raw() {
        return source.toString();
    }

    public CocApiClanWarSummary getClanWarSummary() {
        JSONObject clan = source.getJSONObject("clan");
        JSONObject opponent = source.getJSONObject("opponent");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSS'Z'");
        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime startTime = LocalDateTime.parse(source.getString("startTime").toString(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(source.getString("endTime").toString(), formatter);
        return CocApiClanWarSummary.builder()
                .clanTag(clan.getString("tag"))
                .clanName(clan.getString("name"))
                .clanAttacks(clan.getLong("attacks"))
                .clanStars(clan.getLong("stars"))
                .clanDestructionPercentage(clan.getDouble("destructionPercentage"))
                .opponentTag(opponent.getString("tag"))
                .opponentName(opponent.getString("name"))
                .opponentAttacks(opponent.getLong("attacks"))
                .opponentStars(opponent.getLong("stars"))
                .opponentDestructionPercentage(opponent.getDouble("destructionPercentage"))
                .state(source.getString("state"))
                .startTime(startTime)
                .endTime(endTime)
                .regTime(regTime)
                .build();
    }
    public List<CocApiClanWarAttack> getClanWarAttack() {
        List<CocApiClanWarAttack> attacksResponse = new ArrayList<>();
        JSONArray members = source.getJSONObject("clan").getJSONArray("members");
        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);
        for (int i = 0 ; i < members.length() ; i++) {
            JSONObject member = members.getJSONObject(i);
            String tag = member.getString("tag");
            String name = member.getString("name");
            long townhallLevel = member.getLong("townhallLevel");
            long mapPosition = member.getLong("mapPosition");
            try {
                JSONArray attacks = member.getJSONArray("attacks");
                for (int j = 0; j < attacks.length(); j++) {
                    JSONObject attack = attacks.getJSONObject(j);
                    attacksResponse.add(CocApiClanWarAttack.builder()
                            .tag(tag)
                            .name(name)
                            .townhallLevel(townhallLevel)
                            .mapPosition(mapPosition)
                            .defenderTag(attack.getString("defenderTag"))
                            .stars(attack.getLong("stars"))
                            .destructionPercentage(attack.getDouble("destructionPercentage"))
                            .regTime(regTime)
                            .build());
                }
            }
            catch (JSONException e) {
                attacksResponse.add(CocApiClanWarAttack.builder()
                        .tag(tag)
                        .name(name)
                        .townhallLevel(townhallLevel)
                        .mapPosition(mapPosition)
                        .build());
            }
        }
        return attacksResponse;
    }
}
