package org.example.service;

import org.example.dto.CreateDepartmentDTO;
import org.example.dto.DepartmentDTO;
import org.example.dto.EmployeeDTO;
import org.example.mapper.DepartmentMapper;
import org.example.mapper.EmployeeMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;
    private CreateDepartmentDTO createDepartmentDTO;
    private DepartmentDTO departmentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        department = new Department();
        department.setId(1L);
        department.setName("HR");
        department.setDescription("Human Resources Department");

        createDepartmentDTO = new CreateDepartmentDTO();
        createDepartmentDTO.setName("HR");
        createDepartmentDTO.setDescription("Human Resources Department");

        departmentDTO = new DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setName("HR");
        departmentDTO.setDescription("Human Resources Department");
    }

    @Test
    void testCreateDepartment() {
        when(departmentMapper.fromCreateDtoToEntity(createDepartmentDTO)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentMapper.fromEntityToDepartmentDTO(department)).thenReturn(departmentDTO);

        DepartmentDTO result = departmentService.createDepartment(createDepartmentDTO);

        assertNotNull(result);
        assertEquals("HR", result.getName());
        verify(departmentRepository).save(department);
    }

    @Test
    void testGetEmployeesByDepartmentId() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");

        List<Employee> employees = List.of(employee);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");

        when(departmentRepository.findEmployeesByDepartmentId(1L)).thenReturn(employees);
        when(employeeMapper.fromEntitiesToEmployeeDTOs(employees)).thenReturn(List.of(employeeDTO));

        List<EmployeeDTO> result = departmentService.getEmployeesByDepartmentId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(departmentRepository).findEmployeesByDepartmentId(1L);
    }

    @Test
    void testGetDepartments() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(departmentMapper.fromEntitiesToEmployeeDTOs(List.of(department))).thenReturn(List.of(departmentDTO));

        List<DepartmentDTO> result = departmentService.getDepartments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("HR", result.get(0).getName());
        verify(departmentRepository).findAll();
    }

    @Test
    void testDeleteDepartment() {
        Long departmentId = 1L;
        doNothing().when(departmentRepository).deleteById(departmentId);

        departmentService.deleteDepartment(departmentId);

        verify(departmentRepository).deleteById(departmentId);
    }
}
