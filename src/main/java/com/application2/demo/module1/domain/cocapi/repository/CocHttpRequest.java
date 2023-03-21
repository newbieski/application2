package com.application2.demo.module1.domain.cocapi.repository;


import com.application2.demo.module1.config.ClanConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class CocHttpRequest {
    @Autowired
    ClanConfig clanConfig;
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private HttpEntity<?> entity;

    public CocHttpRequest() {
    }

    public JSONObject getRequest(String url) {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setBearerAuth(clanConfig.getCLAN_API_TOKEN());
        entity = new HttpEntity<>(headers);

        String urlString = clanConfig.getCLAN_API()+url;

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
