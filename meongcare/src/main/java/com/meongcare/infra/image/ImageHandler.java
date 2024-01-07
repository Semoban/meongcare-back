package com.meongcare.infra.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageHandler {

    String uploadImage(MultipartFile multipartFile, ImageDirectory usage);

    void deleteImage(String imageURL);

    String getBucketNameFromUrl(String imageUrl);
}
