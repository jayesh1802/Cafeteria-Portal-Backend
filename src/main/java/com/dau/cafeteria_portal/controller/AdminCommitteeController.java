package com.dau.cafeteria_portal.controller;

import com.dau.cafeteria_portal.dto.MemberDTO;
import com.dau.cafeteria_portal.service.CommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
