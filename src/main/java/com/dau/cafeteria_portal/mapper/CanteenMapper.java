package com.dau.cafeteria_portal.mapper;

import com.dau.cafeteria_portal.dto.CanteenDTO;
import com.dau.cafeteria_portal.entity.Canteen;
import org.springframework.stereotype.Component;

@Component
public class CanteenMapper {

    public CanteenDTO toDTO(Canteen canteen) {
        if (canteen == null) return null;

        CanteenDTO dto = new CanteenDTO();
        dto.setId(canteen.getId());
        dto.setCanteenName(canteen.getCanteenName());
        dto.setInfo(canteen.getInfo());
        dto.setFssaiCertificateUrl(canteen.getFssaiCertificateUrl());
        dto.setImageUrl(canteen.getImageUrl());
        return dto;
    }

    public Canteen toEntity(CanteenDTO dto) {
        if (dto == null) return null;

        Canteen canteen = new Canteen();
        canteen.setId(dto.getId());
        canteen.setCanteenName(dto.getCanteenName());
        canteen.setInfo(dto.getInfo());
        canteen.setFssaiCertificateUrl(dto.getFssaiCertificateUrl());
        canteen.setImageUrl(dto.getImageUrl());
        return canteen;
    }
}
