package com.dau.cafeteria_portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false,unique = true)
    private String emailId;
    @Column(nullable = false)
    private String password;
    // later use enum for this.
    private Role userRole;
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        this.createdAt=LocalDateTime.now();
    }
    public enum Role{
        USER,
        ADMIN
    }

}
