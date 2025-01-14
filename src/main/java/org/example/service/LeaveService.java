package org.example.service;

import org.example.dto.CreateLeaveDTO;
import org.example.dto.LeaveDTO;
import org.example.dto.UpdateLeaveDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.EmployeeMapper;
import org.example.mapper.LeaveMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Leave;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public LeaveService(LeaveRepository leaveRepository, LeaveMapper leaveMapper, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public LeaveDTO applyForLeave(CreateLeaveDTO createLeaveDTO) {
        Optional<Employee> employeeOptional = employeeRepository.findById(createLeaveDTO.getEmployeeId());
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found with ID: " + createLeaveDTO.getEmployeeId());
        }

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
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));

        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);

        List<Leave> allLeaves = employees.stream()
                .flatMap(employee -> employee.getLeaves().stream())
                .collect(Collectors.toList());

        return leaveMapper.fromEntitiesToLeavesDTOs(allLeaves);
    }
}
