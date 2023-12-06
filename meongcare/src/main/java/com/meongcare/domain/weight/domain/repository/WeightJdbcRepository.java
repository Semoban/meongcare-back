package com.meongcare.domain.weight.domain.repository;

import com.meongcare.domain.weight.domain.entity.Weight;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class WeightJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveWeight(List<Weight> weights) {
        String saveWeightSql = "INSERT INTO weight (date, dog_id, kg) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(saveWeightSql,
                weights,
                weights.size(),
                (ps, weight) -> {
                    ps.setDate(1, Date.valueOf(weight.getDate()));
                    ps.setLong(2, weight.getDogId());
                    ps.setDouble(3, weight.getKg());
                }
        );
    }
}
