package com.dau.cafeteria_portal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeedbackQuestionDTO {
    @NotNull
    private Long canteenId;
    private String question;

}
