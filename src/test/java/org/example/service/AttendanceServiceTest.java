package org.example.service;

import org.example.dto.AttendanceDTO;
import org.example.dto.CreateAttendanceDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.AttendanceMapper;
import org.example.model.Attendance;
import org.example.model.Employee;
import org.example.repository.AttendanceRepository;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private AttendanceMapper attendanceMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AttendanceService attendanceService;

    private Attendance attendance;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");

        attendance = new Attendance();
        attendance.setId(1L);
        attendance.setEmployee(employee);
        attendance.setCheckIn(LocalTime.of(9, 0));
        attendance.setCheckOut(LocalTime.of(17, 0));
    }

    @Test
    void testCheckIn() {
        CreateAttendanceDTO createAttendanceDTO = new CreateAttendanceDTO();
        createAttendanceDTO.setEmployeeId(1L);
        createAttendanceDTO.setCheckInTime(LocalTime.of(9, 0));

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
        when(attendanceMapper.fromCreateDtoToEntity(createAttendanceDTO)).thenReturn(attendance);
        when(attendanceMapper.fromEntityToAttendanceDTO(attendance)).thenReturn(new AttendanceDTO());

        AttendanceDTO result = attendanceService.checkIn(createAttendanceDTO);

        assertNotNull(result);
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    void testCheckOut() {
        Long attendanceId = 1L;
        LocalTime checkOutTime = LocalTime.of(17, 0);

        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.of(attendance));
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
        when(attendanceMapper.fromEntityToAttendanceDTO(attendance)).thenReturn(new AttendanceDTO());

        AttendanceDTO result = attendanceService.checkOut(attendanceId, checkOutTime);

        assertNotNull(result);
        assertEquals(checkOutTime, attendance.getCheckOut());
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    void testCheckOutAttendanceNotFound() {
        Long attendanceId = 1L;
        LocalTime checkOutTime = LocalTime.of(17, 0);

        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            attendanceService.checkOut(attendanceId, checkOutTime);
        });

        assertEquals("Attendance record not found with ID: " + attendanceId, exception.getMessage());
    }

    @Test
    void testGetAttendanceRecordsByEmployeeId() {
        Long employeeId = 1L;

        when(attendanceRepository.findByEmployeeId(employeeId)).thenReturn(List.of(attendance));
        when(attendanceMapper.fromEntitiesToAttendanceDTOs(anyList())).thenReturn(List.of(new AttendanceDTO()));

        List<AttendanceDTO> result = attendanceService.getAttendanceRecordsByEmployeeId(employeeId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(attendanceRepository).findByEmployeeId(employeeId);
    }
}
