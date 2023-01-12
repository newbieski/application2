package com.application2.demo.web;

import com.application2.demo.config.ClanConfig;
import com.application2.demo.service.capitalraidresult.CapitalRaidResultService;
import com.application2.demo.web.dto.CapitalRaidResultSaveRequestDto;
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
public class CapitialRaidController {
    private Logger logger = LoggerFactory.getLogger(CapitialRaidController.class);
    @Autowired
    ClanConfig clanConfig;
    private final CapitalRaidResultService capitalRaidResultService;
    @PostMapping("/capitalraid")
    public String capitalraid() {
        JSONObject response = getCapitalRaidSeasons();
        StringBuffer result = new StringBuffer();
        JSONArray items = response.getJSONArray("items");
        JSONArray members = items.getJSONObject(0).getJSONArray("members");
        result.append("start : ");
        result.append(items.getJSONObject(0).get("startTime"));
        result.append("end : ");
        result.append(items.getJSONObject(0).get("endTime"));
        result.append("<br>");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSS'Z'");
        LocalDateTime regTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime startTime = LocalDateTime.parse(items.getJSONObject(0).get("startTime").toString(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(items.getJSONObject(0).get("endTime").toString(), formatter);
        for (int i = 0 ; i < members.length() ; i++) {
            JSONObject member = members.getJSONObject(i);
            CapitalRaidResultSaveRequestDto capitalRaidResultSaveRequestDto = CapitalRaidResultSaveRequestDto.builder()
                    .name(member.get("name").toString())
                    .tag(member.get("tag").toString())
                    .attackCount(Long.valueOf(member.get("attacks").toString()))
                    .startTime(startTime)
                    .endTime(endTime)
                    .regTime(regTime)
                    .build();
            Long id = capitalRaidResultService.save(capitalRaidResultSaveRequestDto);

            result.append(member.get("name"));
            result.append(" ");
            result.append(member.get("attacks"));
            result.append(" ");
            result.append(id);
            result.append("<br>");
        }
        return "200";
    }

    @GetMapping("/capitalraid")
    public String saveCapitalraid() {
        JSONObject response = getCapitalRaidSeasons();
        StringBuffer result = new StringBuffer();
        JSONArray items = response.getJSONArray("items");
        JSONArray members = items.getJSONObject(0).getJSONArray("members");
        result.append("start : ");
        result.append(items.getJSONObject(0).get("startTime"));
        result.append("end : ");
        result.append(items.getJSONObject(0).get("endTime"));
        result.append("<br>");
        for (int i = 0 ; i < members.length() ; i++) {
            JSONObject member = members.getJSONObject(i);
            result.append(member.get("name"));
            result.append(" ");
            result.append(member.get("attacks"));
            result.append("<br>");
        }
        return result.toString();
    }
    private JSONObject getCapitalRaidSeasons() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(clanConfig.getCLAN_API_TOKEN());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String urlString = clanConfig.getCLAN_API()+"/clans"+"/"+ clanConfig.getCLAN_TAG()+"/capitalraidseasons";
                /*
        UriComponents url = UriComponentsBuilder
                .fromUriString(urlString)
                .build();
                 */
        URI uri = null;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        //return response.getBody().toString();
        return new JSONObject(response.getBody().toString());
    }
}
