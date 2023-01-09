package com.application2.demo.service.clanwarleaguewar;

import com.application2.demo.domain.clanwarleaguewar.ClanWarLeagueWarRepository;
import com.application2.demo.web.dto.ClanWarLeagueWarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClanWarLeagueWarService {
    private final ClanWarLeagueWarRepository clanWarLeagueWarRepository;

    @Transactional
    public Long save(ClanWarLeagueWarDto requestDto) {
        return clanWarLeagueWarRepository.save(requestDto.toEntity()).getId();
    }
}
