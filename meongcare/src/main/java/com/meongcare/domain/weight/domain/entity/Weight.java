package com.meongcare.domain.weight.domain.entity;

import com.meongcare.common.BaseEntity;
import com.meongcare.domain.dog.domain.entity.Dog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @JoinColumn(name = "dog_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dog dog;

    @Builder
    public Weight(Long id, double kg, LocalDate date, Dog dog) {
        this.id = id;
        this.kg = kg;
        this.date = date;
        this.dog = dog;
    }

    public static Weight createWeight(double kg, Dog dog) {
        return Weight.builder()
                .date(LocalDate.now())
                .kg(kg)
                .dog(dog)
                .build();

    }

    public static Weight createBeforeWeight(LocalDate date, long minusDays, double kg, Dog dog) {
        return Weight.builder()
                .date(date.minusDays(minusDays))
                .kg(kg)
                .dog(dog)
                .build();
    }

    public void modifyWeight(Double kg) {
        this.kg = kg;
    }
}
