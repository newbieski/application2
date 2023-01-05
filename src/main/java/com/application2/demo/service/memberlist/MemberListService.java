package com.application2.demo.service.memberlist;

import com.application2.demo.domain.memberlist.MemberListRepository;
import com.application2.demo.web.dto.MemberListSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberListService {
    private final MemberListRepository memberListRepository;

    @Transactional
    public Long save(MemberListSaveRequestDto requestDto) {
        return memberListRepository.save(requestDto.toEntity()).getId();
    }
}
