package org.example.unit.test_service;

import org.example.dto.AttendanceDTO;
import org.example.dto.CreateAttendanceDTO;
import org.example.model.Attendance;
import org.example.model.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MockAttendanceService {

    public Attendance getMockedAttendance() {
        Attendance attendance = new Attendance();
        attendance.setId(1L);
        attendance.setEmployee(getMockedEmployee());
        attendance.setDate(LocalDate.now());
        attendance.setCheckIn(LocalTime.of(9, 0));
        attendance.setCheckOut(LocalTime.of(17, 0));
        return attendance;
    }

    public Employee getMockedEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        return employee;
    }

    public CreateAttendanceDTO getMockedCreateAttendanceDTO() {
        CreateAttendanceDTO createAttendanceDTO = new CreateAttendanceDTO();
        createAttendanceDTO.setEmployeeId(1L);
        createAttendanceDTO.setCheckInTime(LocalTime.of(9, 0));
        return createAttendanceDTO;
    }

    public AttendanceDTO getMockedAttendanceDTO() {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setId(1L);
        attendanceDTO.setEmployeeName("John Doe");
        attendanceDTO.setDate(LocalDate.now());
        attendanceDTO.setCheckIn(LocalTime.of(9, 0));
        attendanceDTO.setCheckOut(LocalTime.of(17, 0));
        return attendanceDTO;
    }

    public AttendanceDTO getMockedAttendanceDTOAfterCheckIn() {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setId(1L);
        attendanceDTO.setEmployeeName("John Doe");
        attendanceDTO.setDate(LocalDate.now());
        attendanceDTO.setCheckIn(LocalTime.of(9, 0));
        attendanceDTO.setCheckOut(null);
        return attendanceDTO;
    }

    public List<Attendance> getMockedAttendances() {
        return List.of(getMockedAttendance());
    }

    public List<AttendanceDTO> getMockedAttendanceDTOs() {
        return List.of(getMockedAttendanceDTO());
    }
}
