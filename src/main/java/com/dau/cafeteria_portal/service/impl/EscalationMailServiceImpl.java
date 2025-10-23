package com.dau.cafeteria_portal.service.impl;
import jakarta.mail.MessagingException;
import com.dau.cafeteria_portal.service.EscalationMailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;

public class EscalationMailServiceImpl implements EscalationMailService{
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEscalationMail(String toEmail,String subject, String body, String attachment) throws MessagingException{
        MimeMessage message= javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("cmc.dau.portal@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, true);
        if (attachment != null && !attachment.isEmpty()) {
            FileSystemResource file = new FileSystemResource(new File(attachment));
            if (file.exists()) {
                helper.addAttachment(file.getFilename(), file);
            }
        }

        javaMailSender.send(message);
    }
}
