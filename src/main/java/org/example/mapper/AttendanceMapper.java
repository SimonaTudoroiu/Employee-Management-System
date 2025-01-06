package org.example.mapper;

import org.example.dto.CreateAttendanceDTO;
import org.example.model.Attendance;
import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class AttendanceMapper {
    private final EmployeeRepository employeeRepository;

    // Constructor injection for EmployeeRepository to fetch Employee entity by ID
    public AttendanceMapper(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Convert CreateAttendanceDTO to Attendance entity
    public Attendance fromCreateDtoToEntity(CreateAttendanceDTO createAttendanceDTO) {
        Attendance attendance = new Attendance();

        // Map Employee entity based on Employee ID from DTO
        Employee employee = employeeRepository.findById(createAttendanceDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + createAttendanceDTO.getEmployeeId()));
        attendance.setEmployee(employee);

        // Set other properties
        attendance.setDate(createAttendanceDTO.getDate());

        if (createAttendanceDTO.getCheckInTime() != null) {
            attendance.setCheckIn(LocalTime.parse(createAttendanceDTO.getCheckInTime()));
        }

        if (createAttendanceDTO.getCheckOutTime() != null) {
            attendance.setCheckOut(LocalTime.parse(createAttendanceDTO.getCheckOutTime()));
        }

        return attendance;
    }
}
