package com.meongcare.infra.image.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;


@RequiredArgsConstructor
@Component
public class ImageHandlerS3 implements ImageHandler {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Api s3Api;
    private static final String EMPTY_IMAGE_URL = "";

    @Override
    public String uploadImage(MultipartFile multipartFile, ImageDirectory imageDirectory) {
        if (multipartFile.isEmpty()) {
            return EMPTY_IMAGE_URL;
        }
        try (final InputStream inputStream = multipartFile.getInputStream()) {
            final ObjectMetadata objectMetadata = createObjectMetadata(multipartFile);
            final String fileName = createFileName(imageDirectory);
            return s3Api.uploadImage(bucket, fileName, inputStream, objectMetadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImage(String imageURL) {
        s3Api.removeImage(bucket, imageURL);
    }

    private ObjectMetadata createObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }

    private String createFileName(ImageDirectory selectedDirectory) {
        return Arrays.stream(ImageDirectory.values())
                .filter(imageDirectory -> imageDirectory.equals(selectedDirectory))
                .findAny()
                .map(imageDirectory -> imageDirectory.getBaseDirectory().concat(UUID.randomUUID().toString()))
                .orElseThrow(RuntimeException::new);
    }
}
