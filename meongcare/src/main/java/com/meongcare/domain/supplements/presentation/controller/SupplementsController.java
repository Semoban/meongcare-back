package com.meongcare.domain.supplements.presentation.controller;

import com.meongcare.domain.supplements.application.SupplementsService;
import com.meongcare.domain.supplements.presentation.dto.request.SaveSupplementsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Tag(name = "영양제 API")
@RestController
@RequestMapping("/supplements")
@RequiredArgsConstructor
public class SupplementsController {

    private final SupplementsService supplementsService;

    @Operation(description = "영양제 등록")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PostMapping
    public ResponseEntity<Void> saveSupplements(
            @RequestPart(value = "dto") @Valid SaveSupplementsRequest saveSupplementsRequest,
            @RequestPart(value = "file") MultipartFile multipartFile){
        supplementsService.saveSupplements(saveSupplementsRequest, multipartFile);
        return ResponseEntity.ok().build();
    }
    
}
