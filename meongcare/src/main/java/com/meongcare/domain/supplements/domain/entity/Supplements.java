package com.meongcare.domain.supplements.domain.entity;

import com.meongcare.domain.dog.domain.entity.Dog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Supplements {

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

    private int intakeCount;

    private String intakeUnit;

    private LocalDate startDate;

    private LocalDate endDate;

    @Builder
    public Supplements(Dog dog, String name, String brand, String imageUrl, int intakeCycle, int intakeCount, String intakeUnit, LocalDate startDate, LocalDate endDate) {
        this.dog = dog;
        this.name = name;
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.intakeCycle = intakeCycle;
        this.intakeCount = intakeCount;
        this.intakeUnit = intakeUnit;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
