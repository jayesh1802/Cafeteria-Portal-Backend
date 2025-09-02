package com.dau.cafeteria_portal.entity;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity

public class Complaint {
    Long complainId;
    String title;
    String description;
    LocalDateTime createdAt;

    // later update it using enums
    String status;

}
