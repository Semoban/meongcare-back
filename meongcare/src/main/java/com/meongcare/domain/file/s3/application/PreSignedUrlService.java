package com.meongcare.domain.file.s3.application;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.meongcare.domain.file.s3.presentation.dto.response.PreSignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PreSignedUrlService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public PreSignedUrlResponse getPreSignedUrl(String fileName) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 3);

        Date expiration = now.getTime();

        String preSignedUrl = amazonS3Client.generatePresignedUrl(bucket, fileName, expiration, HttpMethod.PUT)
                .toString();
        return new PreSignedUrlResponse(preSignedUrl);
    }

    public void updateImageLink(String imageUrl) {

    }
}
