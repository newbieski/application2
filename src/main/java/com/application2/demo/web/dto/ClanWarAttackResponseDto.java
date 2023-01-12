package com.application2.demo.web.dto;

import java.util.HashMap;

public class ClanWarAttackResponseDto {
    private HashMap<String, Long> mapTotal, mapUsed, mapStars;

    public ClanWarAttackResponseDto() {
        mapTotal = new HashMap<String, Long>();
        mapUsed = new HashMap<String, Long>();
        mapStars = new HashMap<String, Long>();
    }

    public void addTotal(String tag, long total) {
        mapTotal.put(tag, mapTotal.getOrDefault(tag, Long.valueOf(0)) + total);
    }

    public void addUsed(String tag, long used) {
        mapUsed.put(tag, mapUsed.getOrDefault(tag, Long.valueOf(0)) + used);
    }

    public void addStars(String tag, long stars) {
        mapStars.put(tag, mapStars.getOrDefault(tag, Long.valueOf(0)) + stars);
    }

    public long getTotal(String tag) {
        return mapTotal.getOrDefault(tag, Long.valueOf(0));
    }

    public long getUsed(String tag) {
        return mapUsed.getOrDefault(tag, Long.valueOf(0));
    }

    public long getStars(String tag) {
        return mapStars.getOrDefault(tag, Long.valueOf(0));
    }
}
