package org.example.service;

import org.example.dto.UpdateLeaveDTO;
import org.example.mapper.LeaveMapper;
import org.example.model.Leave;
import org.example.repository.LeaveRepository;
import org.springframework.stereotype.Service;

@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;

    public LeaveService(LeaveRepository leaveRepository, LeaveMapper leaveMapper) {
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
    }

    // Apply for leave (set the status to PENDING)
    public Leave applyForLeave(Leave leave) {
        leave.setStatus(Leave.LeaveStatus.PENDING);
        return leaveRepository.save(leave);
    }

    public Leave approveLeave(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found with ID: " + leaveId));

        UpdateLeaveDTO updateLeaveDTO = new UpdateLeaveDTO(Leave.LeaveStatus.APPROVED);

        leaveMapper.fromUpdateDtoToEntity(updateLeaveDTO, leave);

        return leaveRepository.save(leave);
    }

    public Leave rejectLeave(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found with ID: " + leaveId));

        UpdateLeaveDTO updateLeaveDTO = new UpdateLeaveDTO(Leave.LeaveStatus.REJECTED);

        leaveMapper.fromUpdateDtoToEntity(updateLeaveDTO, leave);

        return leaveRepository.save(leave);
    }
}
