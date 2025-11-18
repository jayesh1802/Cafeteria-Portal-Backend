package com.dau.cafeteria_portal.mapper;

import com.dau.cafeteria_portal.dto.ComplaintDTO;
import com.dau.cafeteria_portal.entity.Canteen;
import com.dau.cafeteria_portal.entity.Complaint;
import com.dau.cafeteria_portal.entity.User;

public class ComplaintMapper {

    public static ComplaintDTO toDTO(Complaint complaint) {
        if (complaint == null) {
            return null;
        }
        return new ComplaintDTO(
                complaint.getComplainId(),
                complaint.getTitle(),
                complaint.getDescription(),
                complaint.getCreatedAt(),
                complaint.getComplaintStatus(),
                complaint.getUser() != null ? complaint.getUser().getEmailId() : null,
                complaint.getCanteen()!=null?complaint.getCanteen().getCanteenId():null
        );
    }

    public static Complaint toEntity(ComplaintDTO dto, User user, Canteen canteen) {
        if (dto == null) {
            return null;
        }
        Complaint complaint = new Complaint();
        complaint.setComplainId(dto.getComplainId()); // optional, usually null for new
        complaint.setTitle(dto.getTitle());
        complaint.setDescription(dto.getDescription());
        complaint.setCreatedAt(dto.getCreatedAt());
        complaint.setComplaintStatus(dto.getComplaintStatus());
        complaint.setUser(user);
        complaint.setCanteen(canteen);
        return complaint;
    }
}
