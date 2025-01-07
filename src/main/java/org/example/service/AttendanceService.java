package org.example.service;

import org.example.dto.AttendanceDTO;
import org.example.dto.CreateAttendanceDTO;
import org.example.exception.ResourceNotFoundException;
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

    public AttendanceDTO checkIn(CreateAttendanceDTO createAttendanceDTO) {
        Attendance attendance = attendanceMapper.fromCreateDtoToEntity(createAttendanceDTO);
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.fromEntityToAttendanceDTO(savedAttendance);
    }

    public AttendanceDTO checkOut(Long attendanceId, LocalTime checkOutTime) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found with ID: " + attendanceId));
        attendance.setCheckOut(checkOutTime);
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.fromEntityToAttendanceDTO(savedAttendance);
    }

    public List<AttendanceDTO> getAttendanceRecordsByEmployeeId(Long employeeId) {
        return attendanceMapper.fromEntitiesToAttendanceDTOs(attendanceRepository.findByEmployeeId(employeeId));
    }
}
