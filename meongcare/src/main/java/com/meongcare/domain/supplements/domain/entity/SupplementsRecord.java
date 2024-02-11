package com.meongcare.domain.supplements.domain.entity;

import com.meongcare.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SupplementsRecord extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "supplements_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Supplements supplements;

    @JoinColumn(name = "supplements_time_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SupplementsTime supplementsTime;

    private LocalDate date;

    private boolean intakeStatus;

    @Builder
    public SupplementsRecord(Supplements supplements, SupplementsTime supplementsTime, LocalDate date) {
        this.supplements = supplements;
        this.supplementsTime = supplementsTime;
        this.date = date;
        this.intakeStatus = false;
    }

    public static SupplementsRecord of(Supplements supplements, SupplementsTime supplementsTime, LocalDate date) {
        return SupplementsRecord.builder()
                .supplements(supplements)
                .supplementsTime(supplementsTime)
                .date(date)
                .build();
    }

    public void updateIntakeStatus() {
        this.intakeStatus = !this.intakeStatus;
    }
}
