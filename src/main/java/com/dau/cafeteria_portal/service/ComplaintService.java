package com.dau.cafeteria_portal.service;

import com.dau.cafeteria_portal.dto.ComplaintDTO;

import java.util.List;
import java.util.Optional;

public interface ComplaintService {
    public ComplaintDTO createComplaint(ComplaintDTO dto, String emailId);
    public List<ComplaintDTO> getMyComplaints(String emailId);
    public Optional<ComplaintDTO> getComplaintDetails(Long id);
}
