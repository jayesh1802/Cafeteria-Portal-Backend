package com.dau.cafeteria_portal.service.impl;
import jakarta.mail.MessagingException;
import com.dau.cafeteria_portal.service.EscalationMailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
@Service
@RequiredArgsConstructor
public class EscalationMailServiceImpl implements  EscalationMailService {

    private final JavaMailSender mailSender;

    public void sendEscalationMail(String to, String subject, String htmlBody, File attachmentPath)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        if (attachmentPath != null) {
            FileSystemResource file = new FileSystemResource(attachmentPath);
            helper.addAttachment(attachmentPath.getName(), file);
        }

        mailSender.send(message);
    }

}

