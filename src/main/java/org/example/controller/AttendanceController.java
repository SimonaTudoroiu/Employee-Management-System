package org.example.controller;

import org.example.dto.AttendanceDTO;
import org.example.dto.CreateAttendanceDTO;
import org.example.model.Attendance;
import org.example.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/checkin")
    public AttendanceDTO checkIn(@RequestBody CreateAttendanceDTO attendance) {
        return attendanceService.checkIn(attendance);
    }

    @PostMapping("/checkout")
    public AttendanceDTO checkOut(@RequestParam Long attendanceId, @RequestParam LocalTime checkOutTime) {
        return attendanceService.checkOut(attendanceId, checkOutTime);
    }

    @GetMapping("/{employeeId}")
    public List<AttendanceDTO> getAttendanceRecordsByEmployeeId(@PathVariable Long employeeId) {
        return attendanceService.getAttendanceRecordsByEmployeeId(employeeId);
    }
}
