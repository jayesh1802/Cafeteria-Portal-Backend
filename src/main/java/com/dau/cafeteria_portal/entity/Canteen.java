package com.dau.cafeteria_portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Canteen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String canteenName;
    private String info;
    // attachment photo of canteen..
    private String fssaiCertificateUrl;
    private String imageUrl;
}
