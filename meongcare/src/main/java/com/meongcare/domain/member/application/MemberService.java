package com.meongcare.domain.member.application;

import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.domain.member.presentation.dto.response.GetProfileResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public GetProfileResponseDto getProfile(Long userId) {
        Member member = memberRepository.findByUserId(userId);
        return GetProfileResponseDto.of(
                member.getEmail(),
                member.getProfileImageUrl());
    }

    @Transactional
    public void updateAlarm(Long userId, boolean pushAgreement) {
        Member member = memberRepository.findByUserId(userId);
        member.updatePushAgreement(pushAgreement);
    }
}
