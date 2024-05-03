package com.meongcare.domain.member.application;

import com.meongcare.domain.member.domain.entity.RevokeMember;
import com.meongcare.domain.member.domain.repository.RevokeMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.common.constants.BusinessLogicConstants.REVOKE_MEMBER_EXPIRATION_DATE;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RevokeMemberScheduler {

    private final RevokeMemberRepository revokeMemberRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredRevokeMembers() {
        log.info("매일 자정 90일이 지난 탈퇴 회원 정보 삭제");
        LocalDateTime ninetyDaysAgo = LocalDateTime.now().minusDays(REVOKE_MEMBER_EXPIRATION_DATE);

        List<RevokeMember> expiredRevokeMembers = revokeMemberRepository.findByRevokeDateBefore(ninetyDaysAgo);
        for (RevokeMember revokeMember : expiredRevokeMembers) {
            revokeMemberRepository.delete(revokeMember);
        }
    }
}
