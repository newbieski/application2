package com.application2.demo.service.warleaguetaglist;

import com.application2.demo.domain.warleaguetaglist.WarLeagueTagListRepository;
import com.application2.demo.web.dto.WarLeagueTagListDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WarLeagueTagListService {
    private final WarLeagueTagListRepository warLeagueTagListRepository;

    @Transactional
    public Long save(WarLeagueTagListDto requestDto) {
        return warLeagueTagListRepository.save(requestDto.toEntity()).getId();
    }
}
