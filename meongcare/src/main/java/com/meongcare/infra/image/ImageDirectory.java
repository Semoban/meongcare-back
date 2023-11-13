package com.meongcare.infra.image;

import lombok.Getter;

@Getter
public enum ImageDirectory {
    EXCRETA("meongcare/excreta/"),
    SYMPTOM("meongcare/symptom/"),
    MEDICAL_RECORD("meongcare/medical-record/"),
    DOG("meongcare/dog/")
    ;

    private final String baseDirectory;

    ImageDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }
}
