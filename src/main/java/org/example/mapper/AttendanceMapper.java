package org.example.mapper;

import org.example.dto.AttendanceDTO;
import org.example.dto.CreateAttendanceDTO;
import org.example.model.Attendance;
import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AttendanceMapper {
    private final EmployeeRepository employeeRepository;

    public AttendanceMapper(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Attendance fromCreateDtoToEntity(CreateAttendanceDTO createAttendanceDTO) {
        Attendance attendance = new Attendance();

        Employee employee = employeeRepository.findById(createAttendanceDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + createAttendanceDTO.getEmployeeId()));
        attendance.setEmployee(employee);

        attendance.setDate(LocalDate.now());

        if (createAttendanceDTO.getCheckInTime() != null) {
            attendance.setCheckIn(createAttendanceDTO.getCheckInTime());
        }

        return attendance;
    }

    public AttendanceDTO fromEntityToAttendanceDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setEmployeeName(attendance.getEmployee() != null ? attendance.getEmployee().getName() : null);
        dto.setDate(attendance.getDate());
        dto.setCheckIn(attendance.getCheckIn());
        dto.setCheckOut(attendance.getCheckOut());
        return dto;
    }

    public List<AttendanceDTO> fromEntitiesToAttendanceDTOs(List<Attendance> attendances) {
        List<AttendanceDTO> attendanceDTOs = new ArrayList<>();
        for (Attendance attendance : attendances) {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(attendance.getId());
            dto.setEmployeeName(attendance.getEmployee() != null ? attendance.getEmployee().getName() : null);
            dto.setDate(attendance.getDate());
            dto.setCheckIn(attendance.getCheckIn());
            dto.setCheckOut(attendance.getCheckOut());
            attendanceDTOs.add(dto);
        }
        return attendanceDTOs;
    }
}
