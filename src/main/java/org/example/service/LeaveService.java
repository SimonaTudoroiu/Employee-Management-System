package org.example.service;

import org.example.dto.CreateLeaveDTO;
import org.example.dto.LeaveDTO;
import org.example.dto.UpdateLeaveDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.EmployeeMapper;
import org.example.mapper.LeaveMapper;
import org.example.model.Employee;
import org.example.model.Leave;
import org.example.repository.EmployeeRepository;
import org.example.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final EmployeeRepository employeeRepository;

    public LeaveService(LeaveRepository leaveRepository, LeaveMapper leaveMapper, EmployeeRepository employeeRepository) {
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
        this.employeeRepository = employeeRepository;
    }

    public LeaveDTO applyForLeave(CreateLeaveDTO createLeaveDTO) {
        Leave leave = leaveMapper.fromCreateDtoToEntity(createLeaveDTO);
        Leave savedLeave = leaveRepository.save(leave);

        return leaveMapper.fromEntityToLeaveDTO(savedLeave);
    }

    public LeaveDTO approveLeave(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found with ID: " + leaveId));

        leave.setStatus(Leave.LeaveStatus.APPROVED);

        Leave savedLeave = leaveRepository.save(leave);

        return leaveMapper.fromEntityToLeaveDTO(savedLeave);
    }


    public LeaveDTO rejectLeave(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found with ID: " + leaveId));

        leave.setStatus(Leave.LeaveStatus.REJECTED);

        Leave updatedLeave = leaveRepository.save(leave);

        return leaveMapper.fromEntityToLeaveDTO(updatedLeave);
    }


    public List<LeaveDTO> getAllLeavesByDepartmentId(Long departmentId) {
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);

        List<Leave> allLeaves = employees.stream()
                .flatMap(employee -> employee.getLeaves().stream()) // Combine all leaves from all employees
                .collect(Collectors.toList());

        return leaveMapper.fromEntitiesToLeavesDTOs(allLeaves);
    }
}
