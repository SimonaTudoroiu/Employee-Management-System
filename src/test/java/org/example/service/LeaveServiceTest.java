package org.example.service;

import org.example.dto.CreateLeaveDTO;
import org.example.dto.LeaveDTO;
import org.example.dto.UpdateLeaveDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.LeaveMapper;
import org.example.model.Employee;
import org.example.model.Leave;
import org.example.repository.EmployeeRepository;
import org.example.repository.LeaveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaveServiceTest {

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private LeaveMapper leaveMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private LeaveService leaveService;

    private CreateLeaveDTO createLeaveDTO;
    private UpdateLeaveDTO updateLeaveDTO;
    private Leave leave;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");

        leave = new Leave();
        leave.setId(1L);
        leave.setEmployee(employee);
        leave.setStartDate(LocalDate.now().plusDays(1));
        leave.setEndDate(LocalDate.now().plusDays(5));
        leave.setReason("Vacation");
        leave.setStatus(Leave.LeaveStatus.PENDING);

        createLeaveDTO = new CreateLeaveDTO();
        createLeaveDTO.setEmployeeId(1L);
        createLeaveDTO.setStartDate(LocalDate.now().plusDays(1));
        createLeaveDTO.setEndDate(LocalDate.now().plusDays(5));
        createLeaveDTO.setReason("Vacation");

        updateLeaveDTO = new UpdateLeaveDTO(Leave.LeaveStatus.APPROVED);
    }

    @Test
    void applyForLeave_Success() {
        when(employeeRepository.findById(createLeaveDTO.getEmployeeId())).thenReturn(Optional.of(employee));
        when(leaveMapper.fromCreateDtoToEntity(createLeaveDTO)).thenReturn(leave);
        when(leaveRepository.save(leave)).thenReturn(leave);
        when(leaveMapper.fromEntityToLeaveDTO(leave)).thenReturn(new LeaveDTO());

        LeaveDTO result = leaveService.applyForLeave(createLeaveDTO);

        assertNotNull(result);
        verify(leaveRepository, times(1)).save(leave);
    }

    @Test
    void applyForLeave_EmployeeNotFound() {
        when(leaveMapper.fromCreateDtoToEntity(createLeaveDTO))
                .thenThrow(new ResourceNotFoundException("Employee not found with ID: 1"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> leaveService.applyForLeave(createLeaveDTO));

        assertEquals("Employee not found with ID: 1", exception.getMessage());
        verify(leaveRepository, never()).save(any());
    }


    @Test
    void approveLeave_Success() {
        leave.setStatus(Leave.LeaveStatus.PENDING); // Explicitly set the initial status
        when(leaveRepository.findById(leave.getId())).thenReturn(Optional.of(leave));
        when(leaveRepository.save(leave)).thenReturn(leave);
        when(leaveMapper.fromEntityToLeaveDTO(leave)).thenReturn(new LeaveDTO());

        LeaveDTO result = leaveService.approveLeave(leave.getId());

        assertNotNull(result);
        assertEquals(Leave.LeaveStatus.APPROVED, leave.getStatus()); // Verify status update
        verify(leaveRepository, times(1)).save(leave);
    }



    @Test
    void approveLeave_LeaveNotFound() {
        when(leaveRepository.findById(leave.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> leaveService.approveLeave(leave.getId()));

        assertEquals("Leave not found with ID: 1", exception.getMessage());
        verify(leaveRepository, never()).save(any());
    }


    @Test
    void rejectLeave_Success() {
        when(leaveRepository.findById(leave.getId())).thenReturn(Optional.of(leave));
        doAnswer(invocation -> {
            Leave leaveArg = invocation.getArgument(1);
            leaveArg.setStatus(Leave.LeaveStatus.REJECTED);
            return null;
        }).when(leaveMapper).fromUpdateDtoToEntity(any(UpdateLeaveDTO.class), eq(leave));

        when(leaveRepository.save(leave)).thenReturn(leave);
        when(leaveMapper.fromEntityToLeaveDTO(leave)).thenReturn(new LeaveDTO());

        LeaveDTO result = leaveService.rejectLeave(leave.getId());

        assertNotNull(result);
        assertEquals(Leave.LeaveStatus.REJECTED, leave.getStatus());
        verify(leaveRepository, times(1)).save(leave);
    }


    @Test
    void rejectLeave_LeaveNotFound() {
        when(leaveRepository.findById(leave.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> leaveService.rejectLeave(leave.getId()));

        assertEquals("Leave not found with ID: 1", exception.getMessage());
        verify(leaveRepository, never()).save(any());
    }


    @Test
    void getAllLeavesByDepartmentId_Success() {
        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane Doe");

        Leave leave2 = new Leave();
        leave2.setId(2L);
        leave2.setEmployee(employee2);
        leave2.setStartDate(LocalDate.now().plusDays(3));
        leave2.setEndDate(LocalDate.now().plusDays(7));
        leave2.setReason("Sick Leave");
        leave2.setStatus(Leave.LeaveStatus.PENDING);

        employee.setLeaves(Arrays.asList(leave));
        employee2.setLeaves(Arrays.asList(leave2));

        when(employeeRepository.findByDepartmentId(1L)).thenReturn(Arrays.asList(employee, employee2));
        when(leaveMapper.fromEntitiesToLeavesDTOs(anyList())).thenReturn(Arrays.asList(new LeaveDTO(), new LeaveDTO()));

        List<LeaveDTO> result = leaveService.getAllLeavesByDepartmentId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findByDepartmentId(1L);
        verify(leaveMapper, times(1)).fromEntitiesToLeavesDTOs(anyList());
    }
}
