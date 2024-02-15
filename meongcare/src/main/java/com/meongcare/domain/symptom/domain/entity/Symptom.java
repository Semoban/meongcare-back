package com.meongcare.domain.symptom.domain.entity;

import com.meongcare.common.BaseEntity;
import com.meongcare.domain.dog.domain.entity.Dog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @JoinColumn(name = "dog_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dog dog;

    @Builder
    public Symptom(SymptomType symptomType, String note, LocalDateTime dateTime, Dog dog) {
        this.symptomType = symptomType;
        this.note = note;
        this.dateTime = dateTime;
        this.dog = dog;
    }

    public static Symptom of(SymptomType symptomType, String note, LocalDateTime dateTime, Dog dog) {
        return Symptom.builder()
                .symptomType(symptomType)
                .dateTime(dateTime)
                .note(note)
                .dog(dog)
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
