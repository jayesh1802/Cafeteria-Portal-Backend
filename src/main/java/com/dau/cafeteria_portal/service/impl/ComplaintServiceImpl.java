package com.dau.cafeteria_portal.service.impl;

import com.dau.cafeteria_portal.dto.ComplaintDTO;
import com.dau.cafeteria_portal.entity.Complaint;
import com.dau.cafeteria_portal.entity.User;
import com.dau.cafeteria_portal.enums.ComplaintStatus;
import com.dau.cafeteria_portal.mapper.ComplaintMapper;
import com.dau.cafeteria_portal.repository.ComplaintRepository;
import com.dau.cafeteria_portal.repository.UserRepository;
import com.dau.cafeteria_portal.service.ComplaintService;
import com.dau.cafeteria_portal.service.EscalationMailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private EscalationMailService escalationMailService;


    @Override
    public ComplaintDTO createComplaint(ComplaintDTO dto, String emailId) {
        // find the user by emailId
        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + emailId));

        Complaint complaint = ComplaintMapper.toEntity(dto, user);
        complaint.setComplaintStatus(ComplaintStatus.PENDING); // default

        Complaint saved = complaintRepository.save(complaint);

        return ComplaintMapper.toDTO(saved);
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
    public String escalateComplaint(Long complaintId){
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        //later we can use LLM to generate body...

        String subject = "Mail to Higher Authorities " + complaint.getTitle();
        String body = "<h3>Complaint Escalated</h3>"
                + "<p><b>Title:</b> " + complaint.getTitle() + "</p>"
                + "<p><b>Description:</b> " + complaint.getDescription() + "</p>";
        // add image thing after AWS
        // as of now hard coded then later add the proper image path etc, will have to modify each section for this..
        String attachmentPath="path";
        try{
            escalationMailService.sendEscalationMail(
                    "cmc@dau.ac.in",
                    subject,
                    body,
                    attachmentPath
            );
            return "Complaint escalated and email sent to CMC.";
        }catch(MessagingException e){
//            e.printStackTrace();
            throw new RuntimeException("Failed to send escalation mail: " + e.getMessage());

        }

    }

}
