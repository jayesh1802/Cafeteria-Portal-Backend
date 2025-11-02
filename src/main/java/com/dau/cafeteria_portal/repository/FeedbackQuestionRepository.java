package com.dau.cafeteria_portal.repository;

import com.dau.cafeteria_portal.entity.FeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackQuestionRepository extends JpaRepository<FeedbackQuestion,Long> {
    List<FeedbackQuestion> findByCanteenId(Long canteenId);
}
