package com.application2.demo.web;

import com.application2.demo.config.ClanConfig;
import com.application2.demo.service.clanwarleaguetaglist.ClanWarLeagueTagListService;
import com.application2.demo.web.dto.ClanWarLeagueTagListDto;
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

@RequiredArgsConstructor
@ResponseBody
@RestController
public class ClanWarLeagueController {
    private Logger logger = LoggerFactory.getLogger(ClanWarLeagueController.class);
    @Autowired
    ClanConfig clanConfig;
    private final ClanWarLeagueTagListService clanWarLeagueTagListService;
    
    @GetMapping("/clanwarleague/taglist")
    public String getTagList() {
        return getClanWarligTagList().toString();
    }
    
    @PostMapping("/clanwarleague/taglist")
    public String saveTagList() {
        JSONObject response = getClanWarligTagList();
        
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
    private String yyyymmConverter(String yyyymm) {
        StringBuffer res = new StringBuffer();
        String strs[] = yyyymm.split("-");
        res.append(strs[0]);
        res.append(strs[1]);
        res.append("01000000");
        
        return res.toString();
    }
    
    
    private JSONObject getClanWarligTagList() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(clanConfig.getCLAN_API_TOKEN());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String urlString = clanConfig.getCLAN_API()+"/clans"+"/"+ clanConfig.getCLAN_TAG()+"/currentwar/leaguegroup";
        URI uri = null;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        
        return new JSONObject(response.getBody().toString());
    }
}
