package com.dau.cafeteria_portal.entity;

import com.dau.cafeteria_portal.enums.FeedbackOption;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FeedbackResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="question_id")
    private FeedbackQuestion question;

    @Enumerated(EnumType.STRING)
    private FeedbackOption option;

    private String reason;

}
