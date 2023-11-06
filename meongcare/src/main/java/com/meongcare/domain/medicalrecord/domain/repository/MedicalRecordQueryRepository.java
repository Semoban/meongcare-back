package com.meongcare.domain.medicalrecord.domain.repository;

import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public void deleteByIds(List<Long> medicalRecordIds) {
        queryFactory
                .delete(medicalRecord)
                .where(medicalRecord.id.in(medicalRecordIds))
                .execute();
    }
}
