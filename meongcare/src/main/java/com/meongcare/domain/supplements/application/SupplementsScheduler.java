package com.meongcare.domain.supplements.application;

import com.meongcare.domain.supplements.domain.entity.Supplements;
import com.meongcare.domain.supplements.domain.entity.SupplementsRecord;
import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import com.meongcare.domain.supplements.domain.repository.SupplementsRecordJdbcRepository;
import com.meongcare.domain.supplements.domain.repository.SupplementsTimeQueryRepository;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsAndTimeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SupplementsScheduler {

    private final SupplementsTimeQueryRepository supplementsTimeQueryRepository;
    private final SupplementsRecordJdbcRepository supplementsRecordJdbcRepository;

    @Transactional
    @Scheduled(cron = "10,30,50 * * * * *", zone = "Asia/Seoul")
    public void createAllSupplements() {
        List<GetSupplementsAndTimeVO> supplementsAndTimeVOS = supplementsTimeQueryRepository.findAll();
        List<SupplementsRecord> supplementsRecords = supplementsAndTimeVOS.stream()
                .map(supplementsAndTimeVO -> createSupplementsRecord(supplementsAndTimeVO))
                .filter(supplementsRecord -> supplementsRecord != null)
                .collect(Collectors.toList());
        supplementsRecordJdbcRepository.saveSupplementsRecords(supplementsRecords);
        log.info("영양제 당일 데이터 추가 성공");
    }

    private SupplementsRecord createSupplementsRecord(GetSupplementsAndTimeVO supplementsAndTimeVO) {
        LocalDate createDate = LocalDate.now().plusDays(1);
        Supplements supplements = supplementsAndTimeVO.getSupplements();
        SupplementsTime supplementsTime = supplementsAndTimeVO.getSupplementsTime();

        if (checkIntakeDate(supplements.getStartDate(), createDate, supplements.getIntakeCycle())) {
            return SupplementsRecord.of(supplements, supplementsTime, createDate);
        }
        return null;
    }

    private boolean checkIntakeDate(LocalDate checkDate, LocalDate createDate, int intakeCycle) {
        while (!createDate.isEqual(checkDate) && createDate.isAfter(checkDate)) {
            checkDate = checkDate.plusDays(intakeCycle);
        }
        if (createDate.isEqual(checkDate)) {
            return true;
        }
        return false;
    }
}
