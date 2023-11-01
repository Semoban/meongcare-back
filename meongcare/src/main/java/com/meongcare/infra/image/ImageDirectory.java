package com.meongcare.infra.image;

import lombok.Getter;

@Getter
public enum ImageDirectory {
    EXCRETA("meongcare/excreta/"),
    SYMPTOM("meongcare/symptom/"),
    ;

    private final String baseDirectory;

    ImageDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }
}
