package com.meongcare.domain.symptom.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SymptomType {
    WEIGHT_LOSS("weightLoss"),
    HIGH_FEVER("highFever"),
    COUGH("cough"),
    DIARRHEA("diarrhea"),
    LOSS_OF_APPETITE("lossOfAppetite"),
    ACTIVITY_DECREASE("activityDecrease"),
    ETC("etc"),
    ;

    private final String symptomString;

    public static SymptomType of(String symptomString) {
        return Arrays.stream(SymptomType.values())
                .filter(symptomType ->  symptomType.getSymptomString().equals(symptomString))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
