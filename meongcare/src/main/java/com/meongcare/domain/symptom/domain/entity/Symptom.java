package com.meongcare.domain.symptom.domain.entity;

import com.meongcare.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Symptom extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private SymptomType symptomType;

    private String note;

    private LocalDateTime dateTime;

    private Long dogId;

    @Builder
    public Symptom(Long id, SymptomType symptomType, String note, LocalDateTime dateTime, Long dogId) {
        this.id = id;
        this.symptomType = symptomType;
        this.note = note;
        this.dateTime = dateTime;
        this.dogId = dogId;
    }

    public static Symptom of(SymptomType symptomType, String note, LocalDateTime dateTime, Long dogId) {
        return Symptom.builder()
                .symptomType(symptomType)
                .dateTime(dateTime)
                .note(note)
                .dogId(dogId)
                .build();
    }

    public void updateRecord(SymptomType symptomType, String note, LocalDateTime dateTime) {
        if (Objects.isNull(note)) {
            this.symptomType = symptomType;
            this.dateTime = dateTime;
            return;
        }
        this.symptomType = symptomType;
        this.dateTime = dateTime;
        this.note = note;
    }
}
