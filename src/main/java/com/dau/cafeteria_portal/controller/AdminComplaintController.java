package com.dau.cafeteria_portal.controller;

import com.dau.cafeteria_portal.dto.ComplaintDTO;
import com.dau.cafeteria_portal.enums.ComplaintStatus;
import com.dau.cafeteria_portal.repository.ComplaintRepository;
import com.dau.cafeteria_portal.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/complaints")
public class AdminComplaintController {
    private final ComplaintService complaintService;
    public AdminComplaintController(ComplaintService complaintService)
    {
        this.complaintService=complaintService;
    }


    @GetMapping("/allComplaints")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints()
    {
        List<ComplaintDTO> complaintDTOs= complaintService.getAllComplaints();
        return ResponseEntity.ok(complaintDTOs);
    }

    @PutMapping("/{complaintId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long complaintId,
            @RequestBody Map<String, String> body) {

        ComplaintStatus status = ComplaintStatus.valueOf(body.get("status"));
        complaintService.updateComplaintStatus(complaintId, status);
        return ResponseEntity.ok("Complaint status updated successfully");
    }


}
