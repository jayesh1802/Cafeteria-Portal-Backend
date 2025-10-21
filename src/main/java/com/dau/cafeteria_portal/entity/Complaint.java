package com.dau.cafeteria_portal.entity;

import com.dau.cafeteria_portal.enums.ComplaintStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complainId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private LocalDateTime createdAt;
    // later update it using enums
    private ComplaintStatus complaintStatus;
    // add attachment later.
    // first i will implement simple APIs
    @PrePersist
    public void onCreate(){
        this.createdAt=LocalDateTime.now();
    }
    @ManyToOne(fetch = FetchType.LAZY)   // many complaints belong to one user
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
