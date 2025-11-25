package com.dau.cafeteria_portal.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.dau.cafeteria_portal.service.S3Service;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${s3.presign.upload.expiryMinutes:5}")
    private long uploadExpiryMinutes;

    @Value("${s3.presign.download.expiryMinutes:5}")
    private long downloadExpiryMinutes;

    @Override
    public String generatePresignedUploadUrl(String key) {
        Date expiration = new Date(System.currentTimeMillis() + uploadExpiryMinutes * 60L * 1000L);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, key)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(req);
        return url.toString();
    }

    @Override
    public String generatePresignedDownloadUrl(String key) {
        Date expiration = new Date(System.currentTimeMillis() + downloadExpiryMinutes * 60L * 1000L);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, key)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(req);
        return url.toString();
    }

    @Override
    public String buildKeyForComplaint(Long complaintId, String originalFileName) {
        // Keep key stable: complaints/{id}/{uuid}_{filename}
        String ext = "";
        int idx = originalFileName.lastIndexOf('.');
        if (idx > 0) ext = originalFileName.substring(idx);
        String uuid = UUID.randomUUID().toString();
        return String.format("complaints/%d/%s%s", complaintId, uuid, ext);
    }
}
