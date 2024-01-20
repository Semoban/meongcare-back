package com.meongcare.domain.supplements.domain.entity;

import com.meongcare.common.BaseEntity;
import com.meongcare.domain.dog.domain.entity.Dog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    @NotNull
    private String name;
    @NotNull
    private String brand;

    private String imageUrl;

    private int intakeCycle;

    private String intakeUnit;

    private LocalDate startDate;

    private boolean isActive;

    private boolean pushAgreement;

    @Builder
    public Supplements(Dog dog, String name, String brand, String imageUrl, int intakeCycle, String intakeUnit, LocalDate startDate) {
        this.dog = dog;
        this.name = name;
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.intakeCycle = intakeCycle;
        this.startDate = startDate;
        this.intakeUnit = intakeUnit;
        this.isActive = true;
        this.pushAgreement = true;
    }

    public void updateActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void updatePushAgreement(boolean pushAgreement) {
        this.pushAgreement = pushAgreement;
    }
}
