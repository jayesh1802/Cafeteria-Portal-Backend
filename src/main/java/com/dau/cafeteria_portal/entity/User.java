package com.dau.cafeteria_portal.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class User {
    @Id
    String userId;
    String userName;
    String emailId;
    String password;
}
