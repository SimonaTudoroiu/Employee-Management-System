package org.example.service;

import org.example.dto.CreateEmployeeDTO;
import org.example.dto.EmployeeDTO;
import org.example.exception.InvalidInputException;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.EmployeeMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private CreateEmployeeDTO createEmployeeDTO;
    private Employee employee;
    private Department department;

    @BeforeEach
    void setUp() {
        createEmployeeDTO = new CreateEmployeeDTO();
        createEmployeeDTO.setName("John Doe");
        createEmployeeDTO.setEmail("john.doe@example.com");
        createEmployeeDTO.setPhone("123456789");
        createEmployeeDTO.setRole("Developer");
        createEmployeeDTO.setDepartmentId(1L);

        department = new Department();
        department.setId(1L);
        department.setName("IT");

        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setEmail("john.doe@example.com");
        employee.setPhone("123456789");
        employee.setRole("Developer");
        employee.setDepartment(department);
    }

    @Test
    void testCreateEmployee_Success() {
        when(departmentRepository.findById(createEmployeeDTO.getDepartmentId())).thenReturn(Optional.of(department));

        when(employeeMapper.fromCreateDtoToEntity(createEmployeeDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.fromEntityToEmployeeDTO(employee)).thenReturn(new EmployeeDTO());

        EmployeeDTO result = employeeService.createEmployee(createEmployeeDTO);

        assertNotNull(result);
        verify(departmentRepository, times(1)).findById(createEmployeeDTO.getDepartmentId());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testCreateEmployee_DepartmentNotFound() {
        when(departmentRepository.findById(createEmployeeDTO.getDepartmentId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.createEmployee(createEmployeeDTO);
        });

        assertEquals("Department not found with ID: 1", exception.getMessage());
    }


    @Test
    void testCreateEmployee_EmptyName() {
        createEmployeeDTO.setName("");

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            employeeService.createEmployee(createEmployeeDTO);
        });

        assertEquals("Employee name cannot be empty.", exception.getMessage());
    }


    @Test
    void testGetEmployeeById_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.fromEntityToEmployeeDTO(employee)).thenReturn(new EmployeeDTO());

        EmployeeDTO result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(1L);
        });

        assertEquals("Employee not found with ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteEmployee_Success() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
