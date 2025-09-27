package com.dau.cafeteria_portal.entity;

import com.dau.cafeteria_portal.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.nio.file.FileStore;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    private String studentId;

    @Column(nullable = false)
    private String name;

//    @Id
    @Column(nullable = false,unique = true)
    private String emailId;

    @Column(nullable = false,unique = true)
    private Long mobileNumber;

    @Column(nullable = false)
    private String password;

//    private Role userRole;
    private Role userRole;
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        this.createdAt=LocalDateTime.now();
    }
}
