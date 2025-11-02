package com.dau.cafeteria_portal.repository;

import com.dau.cafeteria_portal.entity.FeedbackResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackResponseRepository extends JpaRepository<FeedbackResponse,Long> {
    List<FeedbackResponse> findByQuestionId(Long questionId);
}
