package com.application2.demo.module1.web;

import com.application2.demo.module1.config.ClanConfig;
import com.application2.demo.module1.service.clanwarleagueattack.ClanWarLeagueAttackService;
import com.application2.demo.module1.service.clanwarleaguetaglist.ClanWarLeagueTagListService;
import com.application2.demo.module1.service.clanwarleaguewar.ClanWarLeagueWarService;
import com.application2.demo.module1.service.clanwarleaguewar.ClanWarLeagueWarClanSummaryService;

import com.application2.demo.module1.web.dto.ClanWarLeagueAttackSaveRequestDto;
import com.application2.demo.module1.web.dto.ClanWarLeagueTagListSaveRequestDto;
import com.application2.demo.module1.web.dto.ClanWarLeagueWarSaveRequestDto;
import com.application2.demo.module1.web.dto.ClanWarLeagueWarClanSummarySaveRequestDto;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

@RequiredArgsConstructor
@ResponseBody
@RestController
public class ClanWarLeagueController {
    final private Logger logger = LoggerFactory.getLogger(ClanWarLeagueController.class);
    
    @Autowired
    ClanConfig clanConfig;
    private final ClanWarLeagueTagListService clanWarLeagueTagListService;
    private final ClanWarLeagueAttackService clanWarLeagueAttackService;
    private final ClanWarLeagueWarService clanWarLeagueWarService;
    private final ClanWarLeagueWarClanSummaryService clanWarLeagueWarClanSummaryService;
    
    @GetMapping("/clanwarleague/taglist")
    public String getTagList() {
        return getClanWarleagueTagList().toString();
    }
    
    @PostMapping("/clanwarleague/taglist")
    public String saveTagList() {
        JSONObject response = getClanWarleagueTagList();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime clanWarLeagueTime = LocalDateTime.parse(yyyymmConverter(response.get("season").toString()), formatter);
        
        JSONArray rounds = response.getJSONArray("rounds");
        for (int i = 0 ; i < rounds.length() ; i++) {
            JSONArray warTags = rounds.getJSONObject(i).getJSONArray("warTags");
            for (int j = 0 ; j < warTags.length() ; j++) {
                String warTag = warTags.get(j).toString();
                logger.info(warTag);
                if (!"#0".equals(warTag)) {
                    ClanWarLeagueTagListSaveRequestDto clanWarLeagueTagListSaveRequestDto = ClanWarLeagueTagListSaveRequestDto.builder()
                        .tag(warTag)
                        .clanWarLeagueTime(clanWarLeagueTime)
                        .regTime(regTime)
                        .build();
                    clanWarLeagueTagListService.save(clanWarLeagueTagListSaveRequestDto);
                }
            }
        }
        
        return "200";
    }
    
    @GetMapping("/clanwarleague/allwar")
    public String getAllWar() {
        StringBuffer response = new StringBuffer();
        List<String> warTagList = getWarTagList();
        for (String warTag : warTagList) {
            response.append(warTag);
            response.append(" ");
            response.append(getClanWarLeagueWar(warTag));
            response.append("<br>");
        }
        return response.toString();
    }
    
    @PostMapping("/clanwarleague/allwar")
    public String saveAllWar() {
        List<String> warTagList = getWarTagList();
        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSS'Z'");
        for (String warTag : warTagList) {
            JSONObject war = getClanWarLeagueWar(warTag);
            LocalDateTime startTime = LocalDateTime.parse(war.get("startTime").toString(), formatter);
            LocalDateTime endTime = LocalDateTime.parse(war.get("endTime").toString(), formatter);
            ClanWarLeagueWarSaveRequestDto clanWarLeagueWarDto = ClanWarLeagueWarSaveRequestDto.builder()
                                    .warTag(warTag)
                                    .state(war.get("state").toString())
                                    .teamSize(Long.valueOf(war.get("teamSize").toString()))
                                    .startTime(startTime)
                                    .endTime(endTime)
                                    .clanTag(war.getJSONObject("clan").get("tag").toString())
                                    .clanName(war.getJSONObject("clan").get("name").toString())
                                    .opponentTag(war.getJSONObject("opponent").get("tag").toString())
                                    .opponentName(war.getJSONObject("opponent").get("name").toString())
                                    .regTime(regTime)
                                    .build();
            
            clanWarLeagueWarService.save(clanWarLeagueWarDto);
        }
        return "200";
    }

    @GetMapping("/clanwarleague/allattack")
    public String getAllAttack() {
        List<String> warTagList = getWarTagList();
        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);
        StringBuffer sb = new StringBuffer();
        for (String warTag : warTagList) {
            JSONObject war = getClanWarLeagueWar(warTag);
            JSONArray clanMembers = war.getJSONObject("clan").getJSONArray("members");
            JSONArray opponentMembers = war.getJSONObject("opponent").getJSONArray("members");
            List<ClanWarLeagueAttackSaveRequestDto> clanDtoList = extractAttack(clanMembers, warTag, "clan", regTime);
            List<ClanWarLeagueAttackSaveRequestDto> opponentDtoList = extractAttack(opponentMembers, warTag, "opponent", regTime);
            for (ClanWarLeagueAttackSaveRequestDto dto : clanDtoList) {
                sb.append(dto.toString()).append("<br>");
            }
            for (ClanWarLeagueAttackSaveRequestDto dto : opponentDtoList) {
                sb.append(dto.toString()).append("<br>");
            }
        }
        return sb.toString();
    }

    @PostMapping("/clanwarleague/allattack")
    public String saveAllAttack() {
        List<String> warTagList = getWarTagList();
        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);

        for (String warTag : warTagList) {
            JSONObject war = getClanWarLeagueWar(warTag);
            JSONObject clanJson = war.getJSONObject("clan");
            JSONObject opponentJson = war.getJSONObject("opponent");
            JSONArray clanMembers = clanJson.getJSONArray("members");
            JSONArray opponentMembers = opponentJson.getJSONArray("members");
            List<ClanWarLeagueAttackSaveRequestDto> clanDtoList = extractAttack(clanMembers, warTag, "clan", regTime);
            List<ClanWarLeagueAttackSaveRequestDto> opponentDtoList = extractAttack(opponentMembers, warTag, "opponent", regTime);
            for (ClanWarLeagueAttackSaveRequestDto dto : clanDtoList) {
                clanWarLeagueAttackService.save(dto);
            }
            for (ClanWarLeagueAttackSaveRequestDto dto : opponentDtoList) {
                clanWarLeagueAttackService.save(dto);
            }
            
            
            clanWarLeagueWarClanSummaryService.save(extractSummary(clanJson, warTag, regTime));
            clanWarLeagueWarClanSummaryService.save(extractSummary(opponentJson, warTag, regTime));
        }
        return "200";
    }
    
    private ClanWarLeagueWarClanSummarySaveRequestDto extractSummary(JSONObject source, String warTag, LocalDateTime regTime) {
        return ClanWarLeagueWarClanSummarySaveRequestDto.builder()
            .warTag(warTag)
            .clanTag(source.getString("tag"))
            .clanName(source.getString("name"))
            .clanLevel(source.getLong("clanLevel"))
            .attacks(source.getLong("attacks"))
            .stars(source.getLong("stars"))
            .regTime(regTime)
            .build();
    }
    

    private List<ClanWarLeagueAttackSaveRequestDto> extractAttack(JSONArray source, String warTag, String opponent, LocalDateTime regTime) {
        List<ClanWarLeagueAttackSaveRequestDto> resDto = new ArrayList<>();
        for (int i = 0 ; i < source.length() ; i++) {
            JSONObject member = source.getJSONObject(i);
            String attackerTag = member.getString("tag");
            String attackerName = member.getString("name");
            long attackerMapPosition = member.getLong("mapPosition");
            String defenderTag = null;
            long stars = 0;
            double destructionPercentage = 0.0;
            try {
                JSONObject attack = member.getJSONArray("attacks").getJSONObject(0);
                defenderTag = attack.getString("defenderTag");
                stars = attack.getLong("stars");
                destructionPercentage = attack.getDouble("destructionPercentage");
            } catch (JSONException e) {
            }
            resDto.add(ClanWarLeagueAttackSaveRequestDto.builder()
                    .warTag(warTag)
                    .opponent(opponent)
                    .attackerTag(attackerTag)
                    .attackerName(attackerName)
                    .attackerMapPosition(attackerMapPosition)
                    .defenderTag(defenderTag)
                    .stars(stars)
                    .destructionPercentage(destructionPercentage)
                    .regTime(regTime)
                    .build());
        }
        return resDto;
    }
    
    private String yyyymmConverter(String yyyymm) {
        StringBuffer res = new StringBuffer();
        String[] strs = yyyymm.split("-");
        res.append(strs[0]);
        res.append(strs[1]);
        res.append("01000000");
        
        return res.toString();
    }
    
    private String tagConverter(String tag) {
        StringBuffer res = new StringBuffer("%23");
        res.append(tag.substring(1));
        return res.toString();
    }
    
    
    private JSONObject getClanWarleagueTagList() {
        String urlString = clanConfig.getCLAN_API()+"/clans"+"/"+ clanConfig.getCLAN_TAG()+"/currentwar/leaguegroup";
        return cocGetRequest(urlString);
    }
    
    private JSONObject getClanWarLeagueWar(String warTag) {
        warTag = tagConverter(warTag);
        String urlString = clanConfig.getCLAN_API()+"/clanwarleagues/wars/"+ warTag;
        return cocGetRequest(urlString);
    }
    
    private JSONObject cocGetRequest(String urlString) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(clanConfig.getCLAN_API_TOKEN());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        URI uri;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        
        return new JSONObject(response.getBody().toString());
    }
    
    private List<String> getWarTagList() {
        List<String> warTagList = new ArrayList<>();
        JSONObject response = getClanWarleagueTagList();
        JSONArray rounds = response.getJSONArray("rounds");
        for (int i = 0 ; i < rounds.length() ; i++) {
            JSONArray warTags = rounds.getJSONObject(i).getJSONArray("warTags");
            for (int j = 0 ; j < warTags.length() ; j++) {
                String warTag = warTags.get(j).toString();
                if (!"#0".equals(warTag)) {
                    warTagList.add(warTag);
                }
            }
        }
        return warTagList;
    }
}
