package com.application2.demo.module1.service.memberlist;

import com.application2.demo.module1.domain.cocapi.clanmembers.CocApiClanMember;
import com.application2.demo.module1.domain.cocapi.repository.CocClanMembers;
import com.application2.demo.module1.domain.memberlist.MemberListRepository;
import com.application2.demo.module1.domain.memberlist.MemberList;
import com.application2.demo.module1.web.dto.MemberListResponseDto;
import com.application2.demo.module1.web.dto.MemberListSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MemberListService {
    private final MemberListRepository memberListRepository;
    private final CocClanMembers cocClanMembers;

    public void saveClanMembers() {
        cocClanMembers.load();
        List<CocApiClanMember> members = cocClanMembers.getClanMembers();
        for (CocApiClanMember member : members) {
            memberListRepository.save(member.toEntity());
        }
    }
    
    public List<MemberListResponseDto> readLatest() {
        List<MemberList> members = memberListRepository.findAll();
        Collections.sort(members, new Comparator<MemberList>() {
            @Override
            public int compare(MemberList o1, MemberList o2) {
                return o2.getRegTime().compareTo(o1.getRegTime());
            }
        });
        
        List<MemberListResponseDto> response = new ArrayList<>();
        LocalDateTime latest = members.get(0).getRegTime();
        for (int i = 0 ; i < members.size() ; i++) {
            MemberList member = members.get(i);
            if (member.getRegTime().compareTo(latest) != 0) {
                break;
            }
            response.add(MemberListResponseDto.builder()
                        .name(member.getName())
                        .tag(member.getTag())
                        .regTime(member.getRegTime())
                        .build());
        }
        
        return response;
    }
}
