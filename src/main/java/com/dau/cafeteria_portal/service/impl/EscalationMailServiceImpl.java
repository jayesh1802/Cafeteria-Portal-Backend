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
// package com.dau.cafeteria_portal.service.impl;
// ... (imports)

@Service
@RequiredArgsConstructor
public class EscalationMailServiceImpl implements EscalationMailService {

    private final JavaMailSender mailSender;

    public void sendEscalationMail(String to, String subject, String htmlBody, File attachmentPath)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true for multipart/attachment

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        if (attachmentPath != null) {
            FileSystemResource file = new FileSystemResource(attachmentPath);
            String fileName = attachmentPath.getName();
            String mimeType = null;

            // --- ADD LOGIC TO INFER MIME TYPE FROM EXTENSION ---
            if (fileName.toLowerCase().endsWith(".png")) {
                mimeType = "image/png";
            } else if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
                mimeType = "image/jpeg";
            }

            if (mimeType != null) {
                // Use the overload that explicitly sets the MIME type
                helper.addAttachment(fileName, file, mimeType);
            } else {
                // Fallback for other file types, relying on the filename
                helper.addAttachment(fileName, file);
            }
        }

        mailSender.send(message);
    }
}

