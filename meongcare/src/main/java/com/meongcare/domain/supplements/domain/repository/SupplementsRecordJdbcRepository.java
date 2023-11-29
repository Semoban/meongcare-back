package com.meongcare.domain.supplements.domain.repository;

import com.meongcare.domain.supplements.domain.entity.SupplementsRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class SupplementsRecordJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final int BATCH_SIZE = 500;

    public void saveSupplementsRecords(List<SupplementsRecord> supplementsRecords) {
        String saveSupplementsRecordsSql = "INSERT INTO supplements_record (date, intake_status, supplements_id, supplements_time_id) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(saveSupplementsRecordsSql,
                supplementsRecords,
                BATCH_SIZE,
                (ps, supplementsRecord) -> {
                    ps.setDate(1, Date.valueOf(supplementsRecord.getDate()));
                    ps.setBoolean(2, supplementsRecord.isIntakeStatus());
                    ps.setLong(3, supplementsRecord.getSupplements().getId());
                    ps.setLong(4, supplementsRecord.getSupplementsTime().getId());
                }
        );
    }
}
