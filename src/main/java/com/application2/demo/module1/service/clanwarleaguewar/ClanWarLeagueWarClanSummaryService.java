package com.application2.demo.module1.service.clanwarleaguewar;

import com.application2.demo.module1.domain.clanwarleaguewar.ClanWarLeagueWarClanSummaryRepository;
import com.application2.demo.module1.web.dto.ClanWarLeagueWarClanSummarySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClanWarLeagueWarClanSummaryService {
    private final ClanWarLeagueWarClanSummaryRepository clanWarLeagueWarClanSummaryRepository;

    @Transactional
    public Long save(ClanWarLeagueWarClanSummarySaveRequestDto requestDto) {
        return clanWarLeagueWarClanSummaryRepository.save(requestDto.toEntity()).getId();
    }
}
