package com.dau.cafeteria_portal.service.impl;

import com.dau.cafeteria_portal.dto.ComplaintDTO;
import com.dau.cafeteria_portal.entity.Canteen;
import com.dau.cafeteria_portal.entity.Complaint;
import com.dau.cafeteria_portal.entity.User;
import com.dau.cafeteria_portal.enums.ComplaintStatus;
import com.dau.cafeteria_portal.mapper.ComplaintMapper;
import com.dau.cafeteria_portal.repository.CanteenRepository;
import com.dau.cafeteria_portal.repository.ComplaintRepository;
import com.dau.cafeteria_portal.repository.UserRepository;
import com.dau.cafeteria_portal.service.ComplaintService;
import com.dau.cafeteria_portal.service.EscalationMailService;
import com.dau.cafeteria_portal.service.S3Service;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private final ComplaintRepository complaintRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CanteenRepository canteenRepository;
    @Autowired
    private EscalationMailService escalationMailService;
    @Autowired
    private final S3Service s3Service;


    @Override
    public ComplaintDTO createComplaint(ComplaintDTO dto, String emailId, Long canteenId) {

        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Canteen canteen = canteenRepository.findById(canteenId)
                .orElseThrow(() -> new RuntimeException("Canteen not found"));

        Complaint complaint = ComplaintMapper.toEntity(dto, user, canteen);
        complaint.setComplaintStatus(ComplaintStatus.PENDING);

        // Save the complaint WITHOUT imageKey
        Complaint saved = complaintRepository.save(complaint);

        // Build a presigned upload URL for the user to upload the image
        String generatedKey = s3Service.buildKeyForComplaint(saved.getComplainId(), "upload");
        String uploadUrl = s3Service.generatePresignedUploadUrl(generatedKey);

        // Return DTO WITH uploadUrl but WITHOUT saving imageKey yet
        ComplaintDTO result = ComplaintMapper.toDTO(saved);
        result.setUploadUrl(uploadUrl);
        result.setImageKey(generatedKey);   // FRONTEND will use this in /attach

        return result;
    }


    @Override
    public List<ComplaintDTO> getMyComplaints(String emailId) {
        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + emailId));

        return complaintRepository.findByUser(user)
                .stream()
                .map(ComplaintMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<ComplaintDTO> getComplaintDetails(Long id) {
        return complaintRepository.findById(id).map(ComplaintMapper::toDTO);
    }
    @Override
    public List<ComplaintDTO> getAllComplaints(){
        return complaintRepository.findAll()
                .stream()
                .map(ComplaintMapper::toDTO)
                .toList();
    }
    @Override
    public void updateComplaintStatus(Long complaintId,ComplaintStatus complaintStatus){
        Complaint complaint= complaintRepository.findById(complaintId)
                .orElseThrow(()->new RuntimeException("Complaint not found with Id: "+complaintId));
        complaint.setComplaintStatus(complaintStatus);
        complaintRepository.save(complaint);
    }
    @Override
    public String getAdminDownloadUrl(Long id) {
        Complaint c = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        if (c.getImageKey() == null) throw new RuntimeException("No image present");
        return s3Service.generatePresignedDownloadUrl(c.getImageKey());
    }
//    @Override
//    public String escalateComplaint(Long complaintId){
//        Complaint complaint = complaintRepository.findById(complaintId)
//                .orElseThrow(() -> new RuntimeException("Complaint not found"));
//
//        //later we can use LLM to generate body...
//
//        String subject = "Complaint to be escalated" + complaint.getTitle();
//        String body = "<h3>Complaint </h3>"
//                + "<p><b>Title:</b> " + complaint.getTitle() + "</p>"
//                + "<p><b>Description:</b> " + complaint.getDescription() + "</p>";
//        String attachmentPath="path";
//        try{
//            escalationMailService.sendEscalationMail(
//                    "cmc@dau.ac.in",
//                    subject,
//                    body,
//                    attachmentPath
//            );
//            return "Complaint escalated and email sent to CMC.";
//        }catch(MessagingException e){
////            e.printStackTrace();
//            throw new RuntimeException("Failed to send escalation mail: " + e.getMessage());
//
//        }
//
//    }
    @Override
    public String escalateComplaint(Long complaintId) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        String subject = "Complaint to be escalated: " + complaint.getTitle();

        String body =
                "Dear CMC,<br><br>" +
                        "Please look at the following complaint:<br><br>" +
                        "<b>Title:</b> " + complaint.getTitle() + "<br>" +
                        "<b>Description:</b> " + complaint.getDescription() + "<br><br>";

        // ---- Download AWS attachment if exists ----
        String attachmentPath = null;
        if (complaint.getImageKey() != null) {
            attachmentPath = s3Service.downloadToTempFile(complaint.getImageKey());
        }

        try {
            escalationMailService.sendEscalationMail(
                    "cmc@dau.ac.in",
                    subject,
                    body,
                    attachmentPath
            );
            return "Complaint escalated and email sent to CMC.";
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send escalation mail: " + e.getMessage());
        }
    }
    @Override
    public void attachFile(Long complaintId, String fileKey, String emailId) {
        Complaint c = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        // Optional: validate owner
        if (!c.getUser().getEmailId().equals(emailId)) {
            throw new RuntimeException("Unauthorized");
        }

        c.setImageKey(fileKey);
        complaintRepository.save(c);
    }


}
