package org.example.mapper;

import org.example.dto.CreateLeaveDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.LeaveDTO;
import org.example.dto.UpdateLeaveDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Employee;
import org.example.model.Leave;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LeaveMapper {

    private final EmployeeRepository employeeRepository;

    public LeaveMapper(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Leave fromCreateDtoToEntity(CreateLeaveDTO createLeaveDTO) {
        Leave leave = new Leave();

        Employee employee = employeeRepository.findById(createLeaveDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + createLeaveDTO.getEmployeeId()));
        leave.setEmployee(employee);

        leave.setStartDate(createLeaveDTO.getStartDate());
        leave.setEndDate(createLeaveDTO.getEndDate());
        leave.setReason(createLeaveDTO.getReason());
        leave.setStatus(Leave.LeaveStatus.PENDING); // Default status when creating a leave request

        return leave;
    }

    public void fromUpdateDtoToEntity(UpdateLeaveDTO updateLeaveDTO, Leave leave) {
        if (updateLeaveDTO.getStatus() != null) {
            leave.setStatus(updateLeaveDTO.getStatus());
        }
    }

    public LeaveDTO fromEntityToLeaveDTO(Leave leave) {
        LeaveDTO dto = new LeaveDTO();
        dto.setId(leave.getId());
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setReason(leave.getReason());
        dto.setStatus(leave.getStatus());

        if (leave.getEmployee() != null) {
            dto.setEmployeeName(leave.getEmployee().getName());
        }

        return dto;
    }

    public List<LeaveDTO> fromEntitiesToLeavesDTOs(List<Leave> leaves) {
        return leaves.stream()
                .map(this::fromEntityToLeaveDTO)
                .collect(Collectors.toList());
    }
}
