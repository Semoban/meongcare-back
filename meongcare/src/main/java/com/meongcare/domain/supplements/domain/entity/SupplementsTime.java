package com.meongcare.domain.supplements.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "supplements_time", indexes = {
        @Index(name = "idx_intake_time", columnList = "intake_time")
})
public class SupplementsTime {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "supplements_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Supplements supplements;

    @Column(name = "intake_time")
    private LocalTime intakeTime;

    private int intakeCount;

    @Builder
    public SupplementsTime(Supplements supplements, LocalTime intakeTime, int intakeCount) {
        this.supplements = supplements;
        this.intakeTime = intakeTime;
        this.intakeCount = intakeCount;
    }

    public static SupplementsTime of(Supplements supplements, LocalTime intakeTime, int intakeCount) {
        return SupplementsTime.builder()
                .supplements(supplements)
                .intakeTime(intakeTime)
                .intakeCount(intakeCount)
                .build();
    }

    public void updateIntakeTIme(LocalTime updateIntakeTime){
        this.intakeTime = updateIntakeTime;
    }
}
