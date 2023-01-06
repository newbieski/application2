package com.application2.demo.web;

import com.application2.demo.config.ClanConfig;
import com.application2.demo.service.clanwarleaguetaglist.ClanWarLeagueTagListService;
import com.application2.demo.web.dto.ClanWarLeagueTagListDto;
import com.application2.demo.service.clanwarleague.ClanWarLeagueWarService;
import com.application2.demo.web.dto.ClanWarLeagueWarDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
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
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@RequiredArgsConstructor
@ResponseBody
@RestController
public class ClanWarLeagueController {
    private Logger logger = LoggerFactory.getLogger(ClanWarLeagueController.class);
    
    @Autowired
    ClanConfig clanConfig;
    private final ClanWarLeagueTagListService clanWarLeagueTagListService;
    private final ClanWarLeagueWarService clanWarLeagueWarService;
    
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
                    ClanWarLeagueTagListDto clanWarLeagueTagListDto = ClanWarLeagueTagListDto.builder()
                        .tag(warTag)
                        .clanWarLeagueTime(clanWarLeagueTime)
                        .regTime(regTime)
                        .build();
                    clanWarLeagueTagListService.save(clanWarLeagueTagListDto);
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
            response.append(getClanWarLeagueWar(warTag).toString());
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
            ClanWarLeagueWarDto clanWarLeagueWarDto = ClanWarLeagueWarDto.builder()
                                    .state(war.get("state").toString())
                                    .teamSize(Long.valueOf(war.get("teamSize").toString()))
                                    .startTime(startTime)
                                    .endTime(endTime)
                                    .clanTag(war.getJSONObject("clan").get("tag").toString())
                                    .clanName(war.getJSONObject("clan").get("name").toString())
                                    .opponentTag(war.getJSONObject("opponent").get("name").toString())
                                    .opponentName(war.getJSONObject("opponent").get("tag").toString())
                                    .regTime(regTime)
                                    .build();
            
            clanWarLeagueWarService.save(clanWarLeagueWarDto);
            
        }
        return "200";
    }
    
    private String yyyymmConverter(String yyyymm) {
        StringBuffer res = new StringBuffer();
        String strs[] = yyyymm.split("-");
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
        URI uri = null;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        
        return new JSONObject(response.getBody().toString());
    }
    
    private List<String> getWarTagList() {
        List<String> warTagList = new ArrayList<String>();
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
