package com.dau.cafeteria_portal.service;

import jakarta.mail.MessagingException;

public interface EscalationMailService {
    public void sendEscalationMail(String toEmail, String subject,String messageBody, String attachmentPath) throws MessagingException;
}
