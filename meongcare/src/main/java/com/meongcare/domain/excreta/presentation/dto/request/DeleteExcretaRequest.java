package com.meongcare.domain.excreta.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class DeleteExcretaRequest {

    @NotNull
    List<Long> excretaIds;
}
