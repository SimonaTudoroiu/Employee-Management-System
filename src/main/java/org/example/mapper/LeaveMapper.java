package org.example.mapper;

import org.example.dto.CreateLeaveDTO;
import org.example.dto.UpdateLeaveDTO;
import org.example.model.Employee;
import org.example.model.Leave;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class LeaveMapper {

    private final EmployeeRepository employeeRepository;

    public LeaveMapper(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Leave fromCreateDtoToEntity(CreateLeaveDTO createLeaveDTO) {
        Leave leave = new Leave();

        Employee employee = employeeRepository.findById(createLeaveDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + createLeaveDTO.getEmployeeId()));
        leave.setEmployee(employee);

        leave.setStartDate(createLeaveDTO.getStartDate());
        leave.setEndDate(createLeaveDTO.getEndDate());
        leave.setReason(createLeaveDTO.getReason());
        leave.setStatus(Leave.LeaveStatus.PENDING); // Default status when creating a leave request

        return leave;
    }

    public void fromUpdateDtoToEntity(UpdateLeaveDTO updateLeaveDTO, Leave leave) {
        if (updateLeaveDTO.getStatus() != null) {
            // Directly set the LeaveStatus enum value
            leave.setStatus(updateLeaveDTO.getStatus());
        }
    }
}
