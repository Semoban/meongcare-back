package com.meongcare.domain.dog.domain.entity;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Sex {
    MALE("male"),
    FEMALE("female");

    private final String sex;

    Sex(String sex) {
        this.sex = sex;
    }

    public static Sex of(String sex) {
        return Stream.of(Sex.values())
                .filter(p -> p.getSex().equals(sex))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
