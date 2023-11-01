package com.meongcare.domain.excreta.domain.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ExcretaType {
    FECES("FECES"), URINE("URINE"),
    ;

    private final String excretaType;

    ExcretaType(String excretaType) {
        this.excretaType = excretaType;
    }

    public static ExcretaType of(String excretaString) {
        return Arrays.stream(ExcretaType.values())
                .filter(excretaType -> excretaType.getExcretaType().equals(excretaString))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
