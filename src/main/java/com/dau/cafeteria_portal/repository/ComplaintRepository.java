package com.dau.cafeteria_portal.repository;

import com.dau.cafeteria_portal.entity.Complaint;
import com.dau.cafeteria_portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    List<Complaint> findByUser(User user);
}
