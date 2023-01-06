package com.application2.demo.service.capitalraidresult;
import com.application2.demo.domain.capitalraidresult.CapitalRaidResultRepository;
import com.application2.demo.web.dto.CapitalRaidResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CapitalRaidResultService {
    private final CapitalRaidResultRepository capitalRaidResultRepository;

    @Transactional
    public Long save(CapitalRaidResultDto capitalRaidResultDto) {
        return capitalRaidResultRepository.save(capitalRaidResultDto.toEntity()).getId();
    }
}
