package com.dau.cafeteria_portal.controller.admin;

import com.dau.cafeteria_portal.dto.CanteenDTO;
import com.dau.cafeteria_portal.service.CanteenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/canteens")
public class AdminCanteenController {

    private final CanteenService canteenService;

    public AdminCanteenController(CanteenService canteenService) {
        this.canteenService = canteenService;
    }

    @PostMapping
    public ResponseEntity<String> addCanteen(@RequestBody CanteenDTO canteenDTO) {
        canteenService.addCanteen(canteenDTO);
        return ResponseEntity.ok("Canteen added successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCanteen(@PathVariable Long id, @RequestBody CanteenDTO updatedCanteen) {
        canteenService.updateCanteen(id, updatedCanteen);
        return ResponseEntity.ok("Canteen updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCanteen(@PathVariable Long id) {
        canteenService.deleteCanteen(id);
        return ResponseEntity.ok("Canteen deleted successfully!");
    }
}
