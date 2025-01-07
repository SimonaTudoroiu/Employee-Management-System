package org.example.controller;

import org.example.dto.CreateLeaveDTO;
import org.example.dto.LeaveDTO;
import org.example.model.Leave;
import org.example.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    public LeaveDTO applyForLeave(@RequestBody CreateLeaveDTO leave) {
        return leaveService.applyForLeave(leave);
    }

    @PutMapping("/{id}/approve")
    public LeaveDTO approveLeave(@PathVariable Long id) {
        return leaveService.approveLeave(id);
    }

    @PutMapping("/{id}/reject")
    public LeaveDTO rejectLeave(@PathVariable Long id) {
        return leaveService.rejectLeave(id);
    }

    @GetMapping("/{departmentId}")
    public List<LeaveDTO> getAllLeavesByDepartmentId(@PathVariable Long departmentId){
        return leaveService.getAllLeavesByDepartmentId(departmentId);
    }
}
