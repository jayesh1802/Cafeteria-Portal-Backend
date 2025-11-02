package com.dau.cafeteria_portal.dto;

import com.dau.cafeteria_portal.enums.FeedbackOption;
import lombok.Data;

@Data
public class FeedbackSubmissionDTO {
    private Long questionId;
    private FeedbackOption option;
    private String reason;
}
