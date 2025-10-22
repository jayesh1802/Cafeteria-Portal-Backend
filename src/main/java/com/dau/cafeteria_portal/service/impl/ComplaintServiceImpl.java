package com.dau.cafeteria_portal.service.impl;

import com.dau.cafeteria_portal.dto.ComplaintDTO;
import com.dau.cafeteria_portal.entity.Complaint;
import com.dau.cafeteria_portal.entity.User;
import com.dau.cafeteria_portal.enums.ComplaintStatus;
import com.dau.cafeteria_portal.mapper.ComplaintMapper;
import com.dau.cafeteria_portal.repository.ComplaintRepository;
import com.dau.cafeteria_portal.repository.UserRepository;
import com.dau.cafeteria_portal.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

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
}
