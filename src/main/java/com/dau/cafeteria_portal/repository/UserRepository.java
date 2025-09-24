package com.dau.cafeteria_portal.repository;

import com.dau.cafeteria_portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
