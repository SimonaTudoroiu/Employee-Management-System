package org.example.unit.service;

import org.example.dto.AttendanceDTO;
import org.example.dto.CreateAttendanceDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.AttendanceMapper;
import org.example.model.Attendance;
import org.example.model.Employee;
import org.example.repository.AttendanceRepository;
import org.example.repository.EmployeeRepository;
import org.example.service.AttendanceService;
import org.example.unit.test_service.MockAttendanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AttendanceServiceUnitTest {

    private MockAttendanceService mockAttendanceService = new MockAttendanceService();

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private AttendanceMapper attendanceMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AttendanceService attendanceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckIn_success() {
        CreateAttendanceDTO createAttendanceDTO = mockAttendanceService.getMockedCreateAttendanceDTO();
        Attendance mockedAttendance = mockAttendanceService.getMockedAttendance();
        Employee mockedEmployee = mockAttendanceService.getMockedEmployee();

        Mockito.when(employeeRepository.findById(mockedEmployee.getId())).thenReturn(Optional.of(mockedEmployee));
        Mockito.when(attendanceRepository.save(any(Attendance.class))).thenReturn(mockedAttendance);
        Mockito.when(attendanceMapper.fromCreateDtoToEntity(createAttendanceDTO)).thenReturn(mockedAttendance);
        Mockito.when(attendanceMapper.fromEntityToAttendanceDTO(mockedAttendance)).thenReturn(mockAttendanceService.getMockedAttendanceDTO());

        AttendanceDTO result = attendanceService.checkIn(createAttendanceDTO);

        Assertions.assertNotNull(result);
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    public void testCheckOut_success() {
        Attendance mockedAttendance = mockAttendanceService.getMockedAttendance();
        Long attendanceId = mockedAttendance.getId();
        LocalTime checkOutTime = LocalTime.of(17, 0);

        Mockito.when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.of(mockedAttendance));
        Mockito.when(attendanceRepository.save(any(Attendance.class))).thenReturn(mockedAttendance);
        Mockito.when(attendanceMapper.fromEntityToAttendanceDTO(mockedAttendance)).thenReturn(mockAttendanceService.getMockedAttendanceDTO());

        AttendanceDTO result = attendanceService.checkOut(attendanceId, checkOutTime);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(checkOutTime, mockedAttendance.getCheckOut());
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    public void testCheckOutAttendanceNotFound_throwsException() {
        Long attendanceId = 1L;
        LocalTime checkOutTime = LocalTime.of(17, 0);

        Mockito.when(attendanceRepository.findById(attendanceId))
                .thenReturn(Optional.empty());

        Employee mockedEmployee = new Employee();
        mockedEmployee.setId(1L);
        Mockito.when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(mockedEmployee));

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            attendanceService.checkOut(attendanceId, checkOutTime);
        });

        Assertions.assertEquals("Attendance record not found with ID: " + attendanceId, exception.getMessage());
    }

    @Test
    public void testGetAttendanceRecordsByEmployeeId_success() {
        Long employeeId = 1L;

        Employee mockedEmployee = new Employee();
        mockedEmployee.setId(employeeId);

        List<Attendance> mockedAttendances = mockAttendanceService.getMockedAttendances();
        List<AttendanceDTO> mockedAttendanceDTOs = mockAttendanceService.getMockedAttendanceDTOs();

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(mockedEmployee));
        Mockito.when(attendanceRepository.findByEmployeeId(employeeId)).thenReturn(mockedAttendances);
        Mockito.when(attendanceMapper.fromEntitiesToAttendanceDTOs(mockedAttendances)).thenReturn(mockedAttendanceDTOs);

        List<AttendanceDTO> result = attendanceService.getAttendanceRecordsByEmployeeId(employeeId);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        verify(employeeRepository).findById(employeeId);
        verify(attendanceRepository).findByEmployeeId(employeeId);
        verify(attendanceMapper).fromEntitiesToAttendanceDTOs(mockedAttendances);
    }

}
