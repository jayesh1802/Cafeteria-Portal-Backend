package com.dau.cafeteria_portal.service;

import com.dau.cafeteria_portal.dto.FeedbackQuestionDTO;
import com.dau.cafeteria_portal.dto.FeedbackSubmissionDTO;
import com.dau.cafeteria_portal.entity.FeedbackQuestion;
import com.dau.cafeteria_portal.entity.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    // Admin
    FeedbackQuestion addQuestionToCanteen(FeedbackQuestionDTO feedbackQuestionDTO);
    FeedbackQuestion updateQuestion(Long questionId, String newText);
    void deleteQuestion(Long questionId);
    List<FeedbackResponse> getFeedbackForCanteen(Long canteenId);

    // User
    List<FeedbackQuestion> getQuestionsByCanteen(Long canteenId);
    void submitFeedback(List<FeedbackSubmissionDTO> submissions);
}
