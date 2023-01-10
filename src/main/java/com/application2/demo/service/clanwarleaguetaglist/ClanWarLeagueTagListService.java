package com.application2.demo.service.clanwarleaguetaglist;

import com.application2.demo.domain.clanwarleaguetaglist.ClanWarLeagueTagListRepository;
import com.application2.demo.web.dto.ClanWarLeagueTagListSaveRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClanWarLeagueTagListService {
    private final ClanWarLeagueTagListRepository clanWarLeagueTagListRepository;

    @Transactional
    public Long save(ClanWarLeagueTagListSaveRequestDto requestDto) {
        return clanWarLeagueTagListRepository.save(requestDto.toEntity()).getId();
    }
}
