package com.meongcare.domain.file.s3.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PreSignedUrlResponse {
    private String preSignedUrl;
}
