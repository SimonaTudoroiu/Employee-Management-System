package org.example.unit.service;

import org.example.dto.CreateLeaveDTO;
import org.example.dto.LeaveDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.LeaveMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Leave;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.LeaveRepository;
import org.example.service.LeaveService;
import org.example.unit.test_service.MockLeaveService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class LeaveServiceUnitTest {

    private MockLeaveService mockLeaveService = new MockLeaveService();

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private LeaveMapper leaveMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private LeaveService leaveService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testApplyForLeave_success() {
        CreateLeaveDTO createLeaveDTO = mockLeaveService.getMockedCreateLeaveDTO();
        Leave mockedLeave = mockLeaveService.getMockedLeave();
        Employee mockedEmployee = mockLeaveService.getMockedEmployee();

        Mockito.when(employeeRepository.findById(mockedEmployee.getId())).thenReturn(Optional.of(mockedEmployee));
        Mockito.when(leaveRepository.save(any(Leave.class))).thenReturn(mockedLeave);
        Mockito.when(leaveMapper.fromCreateDtoToEntity(createLeaveDTO)).thenReturn(mockedLeave);
        Mockito.when(leaveMapper.fromEntityToLeaveDTO(mockedLeave)).thenReturn(mockLeaveService.getMockedLeaveDTO());

        LeaveDTO result = leaveService.applyForLeave(createLeaveDTO);

        Assertions.assertNotNull(result);
        verify(leaveRepository).save(any(Leave.class));
    }

    @Test
    public void testApplyForLeave_employeeNotFound() {
        CreateLeaveDTO createLeaveDTO = mockLeaveService.getMockedCreateLeaveDTO();
        Mockito.when(employeeRepository.findById(createLeaveDTO.getEmployeeId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            leaveService.applyForLeave(createLeaveDTO);
        });

        Assertions.assertEquals("Employee not found with ID: " + createLeaveDTO.getEmployeeId(), exception.getMessage());
    }

    @Test
    public void testApproveLeave_success() {
        Leave mockedLeave = mockLeaveService.getMockedLeave();
        Long leaveId = mockedLeave.getId();

        Mockito.when(leaveRepository.findById(leaveId)).thenReturn(Optional.of(mockedLeave));
        Mockito.when(leaveRepository.save(any(Leave.class))).thenReturn(mockedLeave);
        Mockito.when(leaveMapper.fromEntityToLeaveDTO(mockedLeave)).thenReturn(mockLeaveService.getMockedApprovedLeaveDTO());

        LeaveDTO result = leaveService.approveLeave(leaveId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Leave.LeaveStatus.APPROVED, result.getStatus());
        verify(leaveRepository).save(any(Leave.class));
    }

    @Test
    public void testApproveLeave_leaveNotFound() {
        Long leaveId = 1L;
        Mockito.when(leaveRepository.findById(leaveId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            leaveService.approveLeave(leaveId);
        });

        Assertions.assertEquals("Leave not found with ID: " + leaveId, exception.getMessage());
    }

    @Test
    public void testRejectLeave_success() {
        Leave mockedLeave = mockLeaveService.getMockedLeave();
        Long leaveId = mockedLeave.getId();

        Mockito.when(leaveRepository.findById(leaveId)).thenReturn(Optional.of(mockedLeave));
        Mockito.when(leaveRepository.save(any(Leave.class))).thenReturn(mockedLeave);
        Mockito.when(leaveMapper.fromEntityToLeaveDTO(mockedLeave)).thenReturn(mockLeaveService.getMockedRejectedLeaveDTO());

        LeaveDTO result = leaveService.rejectLeave(leaveId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Leave.LeaveStatus.REJECTED, result.getStatus());
        verify(leaveRepository).save(any(Leave.class));
    }

    @Test
    public void testRejectLeave_leaveNotFound() {
        Long leaveId = 1L;
        Mockito.when(leaveRepository.findById(leaveId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            leaveService.rejectLeave(leaveId);
        });

        Assertions.assertEquals("Leave not found with ID: " + leaveId, exception.getMessage());
    }

    @Test
    public void testGetAllLeavesByDepartmentId_success() {
        Long departmentId = 1L;

        Department mockedDepartment = new Department();
        mockedDepartment.setId(departmentId);

        Employee mockedEmployee = mockLeaveService.getMockedEmployee();
        List<Employee> mockedEmployees = List.of(mockedEmployee);
        List<LeaveDTO> mockedLeaveDTOs = mockLeaveService.getMockedLeaveDTOs();

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(mockedDepartment));
        Mockito.when(employeeRepository.findByDepartmentId(departmentId)).thenReturn(mockedEmployees);
        Mockito.when(leaveMapper.fromEntitiesToLeavesDTOs(any())).thenReturn(mockedLeaveDTOs);

        List<LeaveDTO> result = leaveService.getAllLeavesByDepartmentId(departmentId);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        verify(departmentRepository).findById(departmentId);
        verify(employeeRepository).findByDepartmentId(departmentId);
        verify(leaveMapper).fromEntitiesToLeavesDTOs(any());
    }

}