package com.dau.cafeteria_portal.service.impl;

import com.dau.cafeteria_portal.dto.FeedbackQuestionDTO;
import com.dau.cafeteria_portal.dto.FeedbackSubmissionDTO;
import com.dau.cafeteria_portal.entity.Canteen;
import com.dau.cafeteria_portal.entity.FeedbackQuestion;
import com.dau.cafeteria_portal.entity.FeedbackResponse;
import com.dau.cafeteria_portal.repository.CanteenRepository;
import com.dau.cafeteria_portal.repository.FeedbackQuestionRepository;
import com.dau.cafeteria_portal.repository.FeedbackResponseRepository;
import com.dau.cafeteria_portal.service.FeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackQuestionRepository questionRepo;
    private final FeedbackResponseRepository responseRepo;
    private final CanteenRepository canteenRepo;

    public FeedbackServiceImpl(FeedbackQuestionRepository questionRepo,
                               FeedbackResponseRepository responseRepo,
                               CanteenRepository canteenRepo) {
        this.questionRepo = questionRepo;
        this.responseRepo = responseRepo;
        this.canteenRepo = canteenRepo;
    }

    // 🟢 Admin: Add question to canteen
    @Override
    public FeedbackQuestion addQuestionToCanteen(FeedbackQuestionDTO dto) {
        Canteen canteen = canteenRepo.findById(dto.getCanteenId())
                .orElseThrow(() -> new RuntimeException("Canteen not found"));

        FeedbackQuestion question = new FeedbackQuestion();
        question.setQuestionText(dto.getQuestion());
        question.setCanteen(canteen);

        return questionRepo.save(question);
    }

    //  Admin: Update question text
    @Override
    public FeedbackQuestion updateQuestion(Long questionId, String newText) {
        FeedbackQuestion question = questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        question.setQuestionText(newText);
        return questionRepo.save(question);
    }

    // 🟢 Admin: Delete question
    @Override
    public void deleteQuestion(Long questionId) {
        if (!questionRepo.existsById(questionId)) {
            throw new RuntimeException("Question not found");
        }
        questionRepo.deleteById(questionId);
    }

    // 🟢 Admin: View all feedback responses for a canteen
    @Override
    public List<FeedbackResponse> getFeedbackForCanteen(Long canteenId) {
        return responseRepo.findByQuestionId(canteenId);
    }

    // 🟢 User: Get all feedback questions for a canteen
    @Override
    public List<FeedbackQuestion> getQuestionsByCanteen(Long canteenId) {
        return questionRepo.findByCanteen_CanteenId(canteenId);
    }

    // User: Submit feedback
    @Override
    public void submitFeedback(Long canteenId, List<FeedbackSubmissionDTO> submissions) {

        Canteen canteen = canteenRepo.findById(canteenId)
                .orElseThrow(() -> new RuntimeException("Canteen not found"));

        for (FeedbackSubmissionDTO dto : submissions) {

            FeedbackQuestion question = questionRepo.findById(dto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            // Validate: question must belong to canteen
            if (!question.getCanteen().getCanteenId().equals(canteenId)) {
                throw new RuntimeException("Question " + dto.getQuestionId() +
                        " does not belong to canteen " + canteenId);
            }

            FeedbackResponse response = new FeedbackResponse();
            response.setQuestion(question);
            response.setOption(dto.getOption());
            response.setReason(dto.getReason());
            response.setCreatedAt(LocalDateTime.now());
            responseRepo.save(response);
        }
    }
}
