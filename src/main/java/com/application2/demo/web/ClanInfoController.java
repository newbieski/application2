package com.application2.demo.web;

import com.application2.demo.config.ClanConfig;
import com.application2.demo.domain.memberlist.MemberList;
import com.application2.demo.service.memberlist.MemberListService;
import com.application2.demo.web.dto.MemberListSaveRequestDto;
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

@RequiredArgsConstructor
@ResponseBody
@RestController
public class ClanInfoController {
    private Logger logger = LoggerFactory.getLogger(ClanInfoController.class);
    @Autowired
    ClanConfig clanConfig;
    private final MemberListService memberListService;
    @GetMapping("/myclan/current/members")
    public String getCurrentMembers() {
        JSONObject response = getClansMembers();
        JSONArray items = response.getJSONArray("items");
        StringBuffer result = new StringBuffer();
        for (int i = 0 ; i < items.length() ; i++) {
            JSONObject member = items.getJSONObject(i);
            result.append(member.get("name"));
            result.append(" ");
            result.append(member.get("tag"));
            result.append("<br>");
        }
        return result.toString();
    }

    @PostMapping("myclan/current/members")
    public String saveCurrentMembers() {
        JSONObject response = getClansMembers();
        JSONArray items = response.getJSONArray("items");
        StringBuffer result = new StringBuffer();
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        for (int i = 0 ; i < items.length() ; i++) {
            JSONObject member = items.getJSONObject(i);
            MemberListSaveRequestDto requestDto = MemberListSaveRequestDto.builder()
                    .name(member.get("name").toString())
                    .tag(member.get("tag").toString())
                    .time(time)
                    .build();
            Long id = memberListService.save(requestDto);
            result.append(member.get("name"));
            result.append(" ");
            result.append(member.get("tag"));
            result.append(" ");
            result.append(id);
            result.append(" ");
            result.append(time);
            result.append("<br>");

        }
        return result.toString();
    }

    private JSONObject getClansMembers() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(clanConfig.getCLAN_API_TOKEN());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String urlString = clanConfig.getCLAN_API()+"/clans"+"/"+ clanConfig.getCLAN_TAG()+"/members";

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
