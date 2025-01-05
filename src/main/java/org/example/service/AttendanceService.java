package org.example.service;

import org.example.model.Attendance;
import org.example.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance checkIn(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public Attendance checkOut(Long attendanceId, LocalTime checkOutTime) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance record not found with ID: " + attendanceId));
        attendance.setCheckOut(checkOutTime);
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceRecordsByEmployeeId(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }
}
