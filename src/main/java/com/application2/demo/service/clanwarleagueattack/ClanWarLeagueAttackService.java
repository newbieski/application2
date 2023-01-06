package com.application2.demo.service.clanwarleagueattack;

import com.application2.demo.domain.clanwarleagueattack.ClanWarLeagueAttack;
import com.application2.demo.domain.clanwarleagueattack.ClanWarLeagueAttackRepository;
import com.application2.demo.web.dto.ClanWarLeagueAttackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClanWarLeagueAttackService {
    private final ClanWarLeagueAttackRepository clanWarLeagueAttackRepository;

    @Transactional
    public Long save(ClanWarLeagueAttackDto requestDto) {
        return clanWarLeagueAttackRepository.save(requestDto.toEntity()).getId();
    }

}
