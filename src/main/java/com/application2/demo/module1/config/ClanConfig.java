package com.application2.demo.module1.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ClanConfig {
    @Value("${clan.tag}")
    private String CLAN_TAG;

    @Value("${clan.url}")
    private String CLAN_API;

    @Value("${clan.api_token}")
    private String CLAN_API_TOKEN;
}
