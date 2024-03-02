package com.meongcare.domain.file.s3.presentation;

import com.meongcare.domain.file.s3.application.PreSignedUrlService;
import com.meongcare.domain.file.s3.presentation.dto.response.PreSignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aws/s3")
public class AwsS3Controller {

    private final PreSignedUrlService preSignedUrlService;

    @GetMapping
    public ResponseEntity<PreSignedUrlResponse> getPreSignedUrl(@RequestParam String fileName) {
        return ResponseEntity.ok(preSignedUrlService.getPreSignedUrl(fileName));
    }

    @PatchMapping
    public ResponseEntity<Void> updateImageLink(@RequestParam String imageUrl) {
        preSignedUrlService.updateImageLink(imageUrl);
        return ResponseEntity.ok().build();
    }
}
