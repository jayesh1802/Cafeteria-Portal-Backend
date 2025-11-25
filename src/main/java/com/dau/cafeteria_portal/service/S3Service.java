package com.dau.cafeteria_portal.service;

public interface S3Service {
    String generatePresignedUploadUrl(String key);
    String generatePresignedDownloadUrl(String key);
    String buildKeyForComplaint(Long complaintId, String originalFileName);
}
