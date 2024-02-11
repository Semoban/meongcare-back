package com.meongcare.domain.medicalrecord.domain.repository;

import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import com.meongcare.domain.medicalrecord.domain.repository.vo.GetMedicalRecordsVo;
import com.meongcare.domain.medicalrecord.domain.repository.vo.QGetMedicalRecordsVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.domain.feed.domain.entity.QFeedRecord.feedRecord;
import static com.meongcare.domain.medicalrecord.domain.entity.QMedicalRecord.*;

@RequiredArgsConstructor
@Repository
public class MedicalRecordQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<MedicalRecord> getByIds(List<Long> medicalRecordIds) {
        return queryFactory
                .selectFrom(medicalRecord)
                .where(medicalRecord.id.in(medicalRecordIds))
                .fetch();
    }

    public void deleteMedicalRecords(List<Long> medicalRecordIds) {
        queryFactory
                .update(feedRecord)
                .set(feedRecord.deleted, true)
                .where(medicalRecord.id.in(medicalRecordIds))
                .execute();
    }

    public List<GetMedicalRecordsVo> getByDate(Long dogId, LocalDateTime nowDateTime, LocalDateTime nextDateTime) {
        return queryFactory
                .select(new QGetMedicalRecordsVo(
                        medicalRecord.id,
                        medicalRecord.dateTime
                ))
                .from(medicalRecord)
                .where(
                        dogIdEq(dogId),
                        dateTimeGoe(nowDateTime),
                        dateTimeLt(nextDateTime)
                )
                .orderBy(medicalRecord.dateTime.asc())
                .fetch();
    }

    public void deleteMedicalRecordsDogId(Long dogId) {
        queryFactory
                .update(feedRecord)
                .set(feedRecord.deleted, true)
                .where(dogIdEq(dogId))
                .execute();
    }

    private BooleanExpression dogIdEq(Long dogId) {
        return medicalRecord.dog.id.eq(dogId);
    }
    private BooleanExpression dateTimeGoe(LocalDateTime nowDateTime) {
        return medicalRecord.dateTime.goe(nowDateTime);
    }

    private BooleanExpression dateTimeLt(LocalDateTime nextDatetime) {
        return medicalRecord.dateTime.lt(nextDatetime);
    }
}
