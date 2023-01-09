package com.application2.demo.service.clanwarleaguewar;

import com.application2.demo.domain.clanwarleaguewar.ClanWarLeagueWarClanSummaryRepository;
import com.application2.demo.web.dto.ClanWarLeagueWarClanSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClanWarLeagueWarClanSummaryService {
    private final ClanWarLeagueWarClanSummaryRepository clanWarLeagueWarClanSummaryRepository;

    @Transactional
    public Long save(ClanWarLeagueWarClanSummaryDto requestDto) {
        return clanWarLeagueWarClanSummaryRepository.save(requestDto.toEntity()).getId();
    }
}
