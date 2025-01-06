package org.example.service;

import org.example.dto.CreateAttendanceDTO;
import org.example.mapper.AttendanceMapper;
import org.example.model.Attendance;
import org.example.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
    }

    // Method to create a new attendance record using the AttendanceMapper
    public Attendance checkIn(CreateAttendanceDTO createAttendanceDTO) {
        // Convert DTO to Attendance entity using the mapper
        Attendance attendance = attendanceMapper.fromCreateDtoToEntity(createAttendanceDTO);
        // Save the attendance entity to the repository
        return attendanceRepository.save(attendance);
    }

    // Method to check out, without needing DTO conversion
    public Attendance checkOut(Long attendanceId, LocalTime checkOutTime) {
        // Find the attendance record by ID
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance record not found with ID: " + attendanceId));
        // Set the check-out time
        attendance.setCheckOut(checkOutTime);
        // Save the updated attendance record
        return attendanceRepository.save(attendance);
    }

    // Get attendance records by employee ID
    public List<Attendance> getAttendanceRecordsByEmployeeId(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }
}
