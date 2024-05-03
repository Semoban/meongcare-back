package com.meongcare.domain.dog.application;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.EntityNotFoundException;
import com.meongcare.domain.dog.domain.entity.MemberDog;
import com.meongcare.domain.dog.domain.entity.ShareWaiting;
import com.meongcare.domain.dog.domain.repository.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.dog.domain.repository.MemberDogRepository;
import com.meongcare.domain.dog.domain.repository.ShareWaitingRepository;
import com.meongcare.domain.dog.presentation.dto.request.ShareDogRequest;
import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.member.domain.repository.MemberQueryRepository;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.domain.notifciation.domain.dto.FcmNotificationDTO;
import com.meongcare.domain.notifciation.domain.entity.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShareDogService {

    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private final MemberDogRepository memberDogRepository;
    private final DogRepository dogRepository;
    private final ShareWaitingRepository shareWaitingRepository;
    private final ApplicationEventPublisher eventPublisher;

    private static final String ALARM_TITLE_TEXT_FORMAT = "%s님의 강아지 공유 요청";
    private static final String ALARM_BODY_TEXT_FORMAT = "%s의 주인이 되어주세요!";


    public void requestShareDog(Long dogId, ShareDogRequest shareDogRequest, Long requesterId) {
        Member accepter = memberQueryRepository.getMemberByEmail(shareDogRequest.getShareEmail())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Member requester = memberRepository.getMember(requesterId);
        Dog dog = dogRepository.getDog(dogId);

        eventPublisher.publishEvent(FcmNotificationDTO.of(createPushAlarmTitle(requester.getEmail()),
                createPushAlarmBody(dog.getName()), accepter.getFcmToken(), NotificationType.SHARE_DOG, accepter.getId(), dogId));

        //공유 대기
        ShareWaiting shareWaiting = ShareWaiting.of(dogId, requesterId, accepter.getId());
        shareWaitingRepository.save(shareWaiting);
    }

    //미완성
    public void acceptShareDog(Long dogId, Long memberId) {
        shareWaitingRepository.deleteByAcceptorIdAndDogId(memberId, dogId);

        Dog dog = dogRepository.getDog(dogId);
        Member member = memberRepository.getMember(memberId);
        memberDogRepository.save(MemberDog.of(member, dog));
    }

    private String createPushAlarmTitle(String requesterEmail) {
        return String.format(ALARM_TITLE_TEXT_FORMAT, requesterEmail);
    }

    private String createPushAlarmBody(String dogName) {
        return String.format(ALARM_BODY_TEXT_FORMAT, dogName);
    }
}
