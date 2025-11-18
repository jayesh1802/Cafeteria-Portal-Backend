package com.dau.cafeteria_portal.repository;

import com.dau.cafeteria_portal.entity.FeedbackResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedbackResponseRepository extends JpaRepository<FeedbackResponse,Long> {
    List<FeedbackResponse> findByQuestionId(Long questionId);
    List<FeedbackResponse> findAllByCanteen_CanteenIdAndCreatedAtBetween(
            Long canteenId,
            LocalDateTime start,
            LocalDateTime end
    );
}
