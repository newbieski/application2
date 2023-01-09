package com.application2.demo.web.dto;

import com.application2.demo.domain.capitalraidresult.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

public class CapitalRaidResponseDto {
    private HashMap<String, Long> map;
    
    public CapitalRaidResponseDto() {
        map = new HashMap<String, Long>();
    }
    
    public void add(String tag, long value) {
        map.put(tag, map.getOrDefault(tag, Long.valueOf(0)) + value);
    }

    public long getAttackCount(String tag) {
        return map.getOrDefault(tag, Long.valueOf(0));
    }
}
