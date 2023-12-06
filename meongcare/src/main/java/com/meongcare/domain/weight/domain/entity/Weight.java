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
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Weight extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private double kg;

    private LocalDate date;

    private Long dogId;

    @Builder
    public Weight(Long id, double kg, LocalDate date, Long dogId) {
        this.id = id;
        this.kg = kg;
        this.date = date;
        this.dogId = dogId;
    }

    public static Weight createWeight(double kg, Long dogId) {
        return Weight.builder()
                .date(LocalDate.now())
                .kg(kg)
                .dogId(dogId)
                .build();

    }

    public static Weight createBeforeWeight(LocalDate date, long minusDays, double kg, Long dogId) {
        return Weight.builder()
                .date(date.minusDays(minusDays))
                .kg(kg)
                .dogId(dogId)
                .build();
    }

    public void modifyWeight(Double kg) {
        this.kg = kg;
    }
}
