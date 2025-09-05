package com.dau.cafeteria_portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    String userId;
    String userName;
    String emailId;
    String password;
}
