package com.meongcare.infra.image.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@NoArgsConstructor
@Component
public class S3Api {

    private AmazonS3 amazonS3;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }
    public String uploadImage(String bucket, String fileName, InputStream inputStream, ObjectMetadata objectMetadata) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
        return getImageURL(bucket, fileName);
    }

    private String getImageURL(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public void removeImage(String bucket, String imageURL) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, imageURL));
    }
}
