package com.dau.cafeteria_portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
    @OneToMany(mappedBy = "canteen",cascade = CascadeType.ALL)
    private List<FeedbackQuestion> feedbackQuestions;
}
