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

    @Builder
    public SupplementsTime(Supplements supplements, LocalTime intakeTime) {
        this.supplements = supplements;
        this.intakeTime = intakeTime;
    }

    public static SupplementsTime of(Supplements supplements, LocalTime intakeTime) {
        return SupplementsTime.builder()
                .supplements(supplements)
                .intakeTime(intakeTime)
                .build();
    }
}
