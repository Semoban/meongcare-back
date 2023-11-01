package com.meongcare.domain.symptom.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class DeleteSymptomRequest {

    @NotNull
    List<Long> symptomIds;
}
