package com.dau.cafeteria_portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complainId;
    private String title;
    private String description;
    private LocalDateTime createdAt;

    // later update it using enums
    private String status;
    // add attachment later..



}
