package com.meongcare.domain.member.application;

import com.meongcare.domain.dog.application.DogService;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.member.domain.entity.RevokeMember;
import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.domain.member.domain.repository.RevokeMemberRepository;
import com.meongcare.domain.member.presentation.dto.request.EditProfileImageRequest;
import com.meongcare.domain.member.presentation.dto.response.GetProfileResponse;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final DogService dogService;

    public GetProfileResponse getProfile(Long memberId) {
        Member member = memberRepository.getMember(memberId);
        return GetProfileResponse.of(
                member.getEmail(),
                member.getProfileImageUrl(),
                member.isPushAgreement());
    }

    @Transactional
    public void updateAlarm(Long memberId, boolean pushAgreement) {
        Member member = memberRepository.getMember(memberId);
        member.updatePushAgreement(pushAgreement);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.getMember(memberId);

        List<Dog> dogs = dogRepository.findAllByMemberAndDeletedFalse(member);
        for (Dog dog : dogs) {
            dogService.deleteDog(dog.getId());
        }

        RevokeMember revokeMember = RevokeMember.from(member.getProviderId());
        revokeMemberRepository.save(revokeMember);
        member.deleteMember();
    }

    @Transactional
    public void updateProfileImage(EditProfileImageRequest request) {
        Member member = memberRepository.getMember(request.getMemberId());
        String bucketName = imageHandler.getBucketNameFromUrl(member.getProfileImageUrl());
        if (bucketName.startsWith(bucket)) {
            imageHandler.deleteImage(member.getProfileImageUrl());
        }
        member.updateProfileImageUrl(request.getImageURL());
    }

}
