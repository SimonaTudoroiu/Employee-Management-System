package org.example.service;

import org.example.model.Leave;
import org.example.repository.LeaveRepository;
import org.springframework.stereotype.Service;

@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public Leave applyForLeave(Leave leave) {
        leave.setStatus(Leave.LeaveStatus.PENDING);
        return leaveRepository.save(leave);
    }

    public Leave approveLeave(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found with ID: " + leaveId));
        leave.setStatus(Leave.LeaveStatus.APPROVED);
        return leaveRepository.save(leave);
    }

    public Leave rejectLeave(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found with ID: " + leaveId));
        leave.setStatus(Leave.LeaveStatus.REJECTED);
        return leaveRepository.save(leave);
    }
}
