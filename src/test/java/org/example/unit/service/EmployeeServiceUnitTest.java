package org.example.unit.service;

import org.example.dto.CreateEmployeeDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.UpdateEmployeeDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.EmployeeMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.example.unit.test_service.MockEmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class EmployeeServiceUnitTest {

    private MockEmployeeService mockEmployeeService = new MockEmployeeService();

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEmployee_success() {
        CreateEmployeeDTO createEmployeeDTO = mockEmployeeService.getMockedCreateEmployeeDTO();
        Employee mockedEmployee = mockEmployeeService.getMockedEmployee();
        EmployeeDTO mockedEmployeeDTO = mockEmployeeService.getMockedEmployeeDTO();

        Mockito.when(departmentRepository.findById(createEmployeeDTO.getDepartmentId())).thenReturn(Optional.of(mockEmployeeService.getMockedDepartment()));
        Mockito.when(employeeMapper.fromCreateDtoToEntity(createEmployeeDTO)).thenReturn(mockedEmployee);
        Mockito.when(employeeRepository.save(mockedEmployee)).thenReturn(mockedEmployee);
        Mockito.when(employeeMapper.fromEntityToEmployeeDTO(mockedEmployee)).thenReturn(mockedEmployeeDTO);

        EmployeeDTO result = employeeService.createEmployee(createEmployeeDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockedEmployeeDTO.getName(), result.getName());
        verify(employeeRepository).save(mockedEmployee);
    }

    @Test
    public void testGetEmployeeById_success() {
        Long employeeId = 1L;
        Employee mockedEmployee = mockEmployeeService.getMockedEmployee();
        EmployeeDTO mockedEmployeeDTO = mockEmployeeService.getMockedEmployeeDTO();

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(mockedEmployee));
        Mockito.when(employeeMapper.fromEntityToEmployeeDTO(mockedEmployee)).thenReturn(mockedEmployeeDTO);

        EmployeeDTO result = employeeService.getEmployeeById(employeeId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockedEmployeeDTO.getId(), result.getId());
        verify(employeeRepository).findById(employeeId);
    }

    @Test
    public void testGetEmployeeById_notFound() {
        Long employeeId = 1L;

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(employeeId);
        });

        Assertions.assertEquals("Employee not found with ID: " + employeeId, exception.getMessage());
    }

    @Test
    public void testUpdateEmployee_success() {
        Long employeeId = 1L;
        UpdateEmployeeDTO updateEmployeeDTO = mockEmployeeService.getMockedUpdateEmployeeDTO();
        Employee existingEmployee = mockEmployeeService.getMockedEmployee();
        Employee updatedEmployee = mockEmployeeService.getMockedUpdatedEmployee();
        EmployeeDTO updatedEmployeeDTO = mockEmployeeService.getMockedUpdatedEmployeeDTO();

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        Mockito.when(employeeMapper.fromUpdateDtoToEntity(employeeId, updateEmployeeDTO)).thenReturn(updatedEmployee);
        Mockito.when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);
        Mockito.when(employeeMapper.fromEntityToEmployeeDTO(updatedEmployee)).thenReturn(updatedEmployeeDTO);

        EmployeeDTO result = employeeService.updateEmployee(employeeId, updateEmployeeDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatedEmployeeDTO.getPhone(), result.getPhone());
        verify(employeeRepository).save(updatedEmployee);
    }

    @Test
    public void testDeleteEmployee_success() {
        Long employeeId = 1L;
        Employee mockedEmployee = mockEmployeeService.getMockedEmployee();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(mockedEmployee));

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository).findById(employeeId);
        verify(employeeRepository).deleteById(employeeId);
    }

    @Test
    public void testDeleteEmployee_notFound() {
        Long employeeId = 1L;

        doThrow(new ResourceNotFoundException("Employee not found with ID: " + employeeId))
                .when(employeeRepository).deleteById(employeeId);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(employeeId);
        });

        Assertions.assertEquals("Employee not found with ID: " + employeeId, exception.getMessage());
    }

    @Test
    public void testGetEmployees_success() {
        List<Employee> mockedEmployees = mockEmployeeService.getMockedEmployees();
        List<EmployeeDTO> mockedEmployeeDTOs = mockEmployeeService.getMockedEmployeeDTOs();

        Mockito.when(employeeRepository.findAll()).thenReturn(mockedEmployees);
        Mockito.when(employeeMapper.fromEntitiesToEmployeeDTOs(mockedEmployees)).thenReturn(mockedEmployeeDTOs);

        List<EmployeeDTO> result = employeeService.getEmployees();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        verify(employeeRepository).findAll();
    }
}
