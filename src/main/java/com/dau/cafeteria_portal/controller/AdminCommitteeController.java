package com.dau.cafeteria_portal.controller;

import com.dau.cafeteria_portal.dto.MemberDTO;
import com.dau.cafeteria_portal.service.CommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/admin/committee")
public class AdminCommitteeController {
    @Autowired
    private CommitteeService service;
    @PostMapping
    public MemberDTO addMember(@RequestBody MemberDTO dto) {
        return service.addMember(dto);
    }

    @PutMapping("/{id}")
    public MemberDTO update(@PathVariable Long id, @RequestBody MemberDTO dto) {
        return service.updateMember(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteMember(id);
    }
    @PostMapping("/{id}/upload-photo")
    public ResponseEntity<String> uploadPhoto(@PathVariable Long id,
                                              @RequestParam("file") MultipartFile file) throws IOException {
        String filename = "committee_" + id + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/committee_photos/" + filename); // change folder as needed
        Files.createDirectories(path.getParent()); // create directories if not exist
        Files.write(path, file.getBytes());

        service.updatePhotoUrl(id, "/committee_photos/" + filename); // relative URL for frontend
        return ResponseEntity.ok("Photo uploaded successfully");
    }

}
