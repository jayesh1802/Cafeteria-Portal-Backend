package com.dau.cafeteria_portal.service;

import com.dau.cafeteria_portal.dto.CanteenDTO;

import java.util.List;

public interface CanteenService {
    public List<CanteenDTO> getAllCanteens();
    public CanteenDTO getCanteenById(Long id);
    public void addCanteen(CanteenDTO canteenDTO);
    public void updateCanteen(Long id, CanteenDTO updatedCanteen);
    public void deleteCanteen(Long id);

}
