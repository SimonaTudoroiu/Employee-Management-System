package org.example.unit.service;

import org.example.dto.CreateDepartmentDTO;
import org.example.dto.DepartmentDTO;
import org.example.dto.EmployeeDTO;
import org.example.mapper.DepartmentMapper;
import org.example.mapper.EmployeeMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.repository.DepartmentRepository;
import org.example.service.DepartmentService;
import org.example.unit.test_service.MockDepartmentService;
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

public class DepartmentServiceUnitTest {

    private MockDepartmentService mockDepartmentService = new MockDepartmentService();

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDepartment_success() {
        CreateDepartmentDTO createDepartmentDTO = mockDepartmentService.getMockedCreateDepartmentDTO();
        Department mockedDepartment = mockDepartmentService.getMockedDepartment();
        DepartmentDTO mockedDepartmentDTO = mockDepartmentService.getMockedDepartmentDTO();

        Mockito.when(departmentMapper.fromCreateDtoToEntity(createDepartmentDTO)).thenReturn(mockedDepartment);
        Mockito.when(departmentRepository.save(mockedDepartment)).thenReturn(mockedDepartment);
        Mockito.when(departmentMapper.fromEntityToDepartmentDTO(mockedDepartment)).thenReturn(mockedDepartmentDTO);

        DepartmentDTO result = departmentService.createDepartment(createDepartmentDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockedDepartmentDTO.getName(), result.getName());
        verify(departmentRepository).save(mockedDepartment);
    }

    @Test
    public void testGetEmployeesByDepartmentId_success() {
        Long departmentId = 1L;
        List<Employee> mockedEmployees = mockDepartmentService.getMockedEmployees();
        List<EmployeeDTO> mockedEmployeeDTOs = mockDepartmentService.getMockedEmployeeDTOs();
        Department mockedDepartment = mockDepartmentService.getMockedDepartment();

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(mockedDepartment));

        Mockito.when(departmentRepository.findEmployeesByDepartmentId(departmentId)).thenReturn(mockedEmployees);
        Mockito.when(employeeMapper.fromEntitiesToEmployeeDTOs(mockedEmployees)).thenReturn(mockedEmployeeDTOs);

        List<EmployeeDTO> result = departmentService.getEmployeesByDepartmentId(departmentId);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        verify(departmentRepository).findById(departmentId);
        verify(departmentRepository).findEmployeesByDepartmentId(departmentId);
    }

    @Test
    public void testGetDepartments_success() {
        List<Department> mockedDepartments = mockDepartmentService.getMockedDepartments();
        List<DepartmentDTO> mockedDepartmentDTOs = mockDepartmentService.getMockedDepartmentDTOs();

        Mockito.when(departmentRepository.findAll()).thenReturn(mockedDepartments);
        Mockito.when(departmentMapper.fromEntitiesToEmployeeDTOs(mockedDepartments)).thenReturn(mockedDepartmentDTOs);

        List<DepartmentDTO> result = departmentService.getDepartments();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        verify(departmentRepository).findAll();
    }

    @Test
    public void testDeleteDepartment_success() {
        Long departmentId = 1L;
        Department mockedDepartment = mockDepartmentService.getMockedDepartment();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(mockedDepartment));

        departmentService.deleteDepartment(departmentId);

        verify(departmentRepository).findById(departmentId);
        verify(departmentRepository).deleteById(departmentId);
    }

    @Test
    public void testDeleteDepartment_notFound() {
        Long departmentId = 1L;

        doThrow(new RuntimeException("Department not found with ID: " + departmentId))
                .when(departmentRepository).deleteById(departmentId);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            departmentService.deleteDepartment(departmentId);
        });

        Assertions.assertEquals("Department not found with ID: " + departmentId, exception.getMessage());
    }
}
