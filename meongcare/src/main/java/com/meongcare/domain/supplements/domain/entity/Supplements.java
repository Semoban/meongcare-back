package com.meongcare.domain.supplements.domain.entity;

import com.meongcare.common.BaseEntity;
import com.meongcare.domain.dog.domain.entity.Dog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Supplements extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "dog_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dog dog;

    private String name;

    private String brand;

    private String imageUrl;

    private int intakeCycle;

    private String intakeUnit;

    private LocalDate startDate;

    private boolean stopStatus;

    @Builder
    public Supplements(Dog dog, String name, String brand, String imageUrl, int intakeCycle, String intakeUnit, LocalDate startDate) {
        this.dog = dog;
        this.name = name;
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.intakeCycle = intakeCycle;
        this.startDate = startDate;
        this.intakeUnit = intakeUnit;
        this.stopStatus = false;
    }
}
