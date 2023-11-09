package com.meongcare.domain.weight.domain.entity;

import com.meongcare.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Weight extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private double kg;

    private LocalDateTime dateTime;

    private Long dogId;

    @Builder
    public Weight(Long id, double kg, LocalDateTime dateTime, Long dogId) {
        this.id = id;
        this.kg = kg;
        this.dateTime = dateTime;
        this.dogId = dogId;
    }

    public static Weight createBeforeWeight(
            LocalDateTime dateTime, long minusDays, double kg, Long dogId
    ) {
        return Weight.builder()
                .dateTime(dateTime.minusDays(minusDays))
                .kg(kg)
                .dogId(dogId)
                .build();
    }

    public void modifyTodayWeight(Double kg) {
        this.kg = kg;
    }
}
