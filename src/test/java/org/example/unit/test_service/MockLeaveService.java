package org.example.unit.test_service;

import org.example.dto.CreateLeaveDTO;
import org.example.dto.LeaveDTO;
import org.example.dto.UpdateLeaveDTO;
import org.example.model.Employee;
import org.example.model.Leave;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MockLeaveService {

    public Employee getMockedEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setLeaves(new ArrayList<>());
        return employee;
    }

    public Leave getMockedLeave() {
        Leave leave = new Leave();
        leave.setId(1L);
        Employee employee = getMockedEmployee();
        leave.setEmployee(employee);
        leave.setStartDate(LocalDate.now().plusDays(1));
        leave.setEndDate(LocalDate.now().plusDays(5));
        leave.setReason("Annual Leave");
        leave.setStatus(Leave.LeaveStatus.PENDING);

        employee.getLeaves().add(leave);

        return leave;
    }

    public CreateLeaveDTO getMockedCreateLeaveDTO() {
        CreateLeaveDTO createLeaveDTO = new CreateLeaveDTO();
        createLeaveDTO.setEmployeeId(1L);
        createLeaveDTO.setStartDate(LocalDate.now().plusDays(1));
        createLeaveDTO.setEndDate(LocalDate.now().plusDays(5));
        createLeaveDTO.setReason("Annual Leave");
        return createLeaveDTO;
    }

    public UpdateLeaveDTO getMockedUpdateLeaveDTO() {
        UpdateLeaveDTO updateLeaveDTO = new UpdateLeaveDTO(Leave.LeaveStatus.APPROVED);
        updateLeaveDTO.setId(1L);
        return updateLeaveDTO;
    }

    public LeaveDTO getMockedLeaveDTO() {
        LeaveDTO leaveDTO = new LeaveDTO();
        leaveDTO.setId(1L);
        leaveDTO.setEmployeeName("John Doe");
        leaveDTO.setStartDate(LocalDate.now().plusDays(1));
        leaveDTO.setEndDate(LocalDate.now().plusDays(5));
        leaveDTO.setReason("Annual Leave");
        leaveDTO.setStatus(Leave.LeaveStatus.PENDING);
        return leaveDTO;
    }

    public LeaveDTO getMockedApprovedLeaveDTO() {
        LeaveDTO leaveDTO = getMockedLeaveDTO();
        leaveDTO.setStatus(Leave.LeaveStatus.APPROVED);
        return leaveDTO;
    }

    public LeaveDTO getMockedRejectedLeaveDTO() {
        LeaveDTO leaveDTO = getMockedLeaveDTO();
        leaveDTO.setStatus(Leave.LeaveStatus.REJECTED);
        return leaveDTO;
    }

    public List<Leave> getMockedLeaves() {
        return List.of(getMockedLeave());
    }

    public List<LeaveDTO> getMockedLeaveDTOs() {
        return List.of(getMockedLeaveDTO());
    }
}