package com.meongcare.domain.weight.domain.repository;

import com.meongcare.domain.weight.domain.entity.Weight;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class WeightJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveWeight(List<Weight> weights) {
        String sql = "INSERT INTO weight (created_at, date_time, dog_id, kg) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                weights,
                weights.size(),
                (ps, weight) -> {
                    ps.setTimestamp(1, Timestamp.valueOf(weight.getDateTime()));
                    ps.setTimestamp(2, Timestamp.valueOf(weight.getDateTime()));
                    ps.setLong(3, weight.getDogId());
                    ps.setDouble(4, weight.getKg());
                }
        );
    }
}
