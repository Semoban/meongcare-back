package com.meongcare.domain.member.application;

import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.member.domain.entity.RevokeMember;
import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.domain.member.domain.repository.RevokeMemberRepository;
import com.meongcare.domain.member.presentation.dto.response.GetProfileResponse;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final MemberRepository memberRepository;
    private final ImageHandler imageHandler;
    private final RevokeMemberRepository revokeMemberRepository;
    private final DogRepository dogRepository;

    public GetProfileResponse getProfile(Long userId) {
        Member member = memberRepository.getUser(userId);
        return GetProfileResponse.of(
                member.getEmail(),
                member.getProfileImageUrl(),
                member.isPushAgreement());
    }

    @Transactional
    public void updateAlarm(Long userId, boolean pushAgreement) {
        Member member = memberRepository.getUser(userId);
        member.updatePushAgreement(pushAgreement);
    }

    @Transactional
    public void deleteMember(Long userId) {
        Member member = memberRepository.getUser(userId);
        dogRepository.deleteByMemberId(member.getId());

        RevokeMember revokeMember = RevokeMember.from(member.getProviderId());
        revokeMemberRepository.save(revokeMember);
        member.deleteMember();
    }

    @Transactional
    public void updateProfileImage(Long userId, MultipartFile multipartFile) {
        Member member = memberRepository.getUser(userId);
        String bucketName = imageHandler.getBucketNameFromUrl(member.getProfileImageUrl());
        if (bucketName.startsWith(bucket)) {
            imageHandler.deleteImage(member.getProfileImageUrl());
        }
        String profileImageUrl = imageHandler.uploadImage(multipartFile, ImageDirectory.MEMBER);
        member.updateProfileImageUrl(profileImageUrl);
    }

}
