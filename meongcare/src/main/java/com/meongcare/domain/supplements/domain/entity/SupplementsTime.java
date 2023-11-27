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
public class SupplementsTime {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "supplements_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Supplements supplements;

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
}
