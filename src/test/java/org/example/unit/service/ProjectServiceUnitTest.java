package org.example.unit.service;

import org.example.dto.CreateProjectDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.ProjectDTO;
import org.example.exception.InvalidInputException;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.EmployeeMapper;
import org.example.mapper.ProjectMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Project;
import org.example.repository.DepartmentRepository;
import org.example.repository.ProjectRepository;
import org.example.repository.EmployeeRepository;
import org.example.service.ProjectService;
import org.example.unit.test_service.MockProjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ProjectServiceUnitTest {

    private MockProjectService mockProjectService = new MockProjectService();

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProject_success() {
        CreateProjectDTO createProjectDTO = mockProjectService.getMockedCreateProjectDTO();
        Project mockedProject = mockProjectService.getMockedProject();
        ProjectDTO mockedProjectDTO = mockProjectService.getMockedProjectDTO();
        Department mockedDepartment = mockProjectService.getMockedDepartment();

        Mockito.when(departmentRepository.findById(createProjectDTO.getDepartmentId()))
                .thenReturn(Optional.of(mockedDepartment));

        Mockito.when(projectMapper.fromCreateDtoToEntity(createProjectDTO)).thenReturn(mockedProject);
        Mockito.when(projectRepository.save(mockedProject)).thenReturn(mockedProject);
        Mockito.when(projectMapper.fromEntityToProjectDTO(mockedProject)).thenReturn(mockedProjectDTO);

        ProjectDTO result = projectService.createProject(createProjectDTO);

        Assertions.assertNotNull(result);
        verify(projectRepository).save(mockedProject);
        verify(departmentRepository).findById(createProjectDTO.getDepartmentId());
    }

    @Test
    public void testAssignEmployeesToProject_success() {
        Long projectId = 1L;
        List<Long> employeeIds = new ArrayList<>(List.of(1L, 2L));
        Project mockedProject = mockProjectService.getMockedProject();
        Employee employee1 = mockProjectService.getMockedEmployee();
        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane Doe");
        employee2.setDepartment(mockProjectService.getMockedDepartment());

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(mockedProject));
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        Mockito.when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee2));
        Mockito.when(projectRepository.save(mockedProject)).thenReturn(mockedProject);
        Mockito.when(projectMapper.fromEntityToProjectDTO(mockedProject)).thenReturn(mockProjectService.getMockedProjectDTO());

        ProjectDTO result = projectService.assignEmployeesToProject(projectId, employeeIds);

        Assertions.assertNotNull(result);
        verify(projectRepository).save(mockedProject);
    }


    @Test
    public void testAssignEmployeesToProject_invalidDepartment_throwsException() {
        Long projectId = 1L;
        List<Long> employeeIds = List.of(1L);
        Project mockedProject = mockProjectService.getMockedProject();
        Employee mockedEmployee = mockProjectService.getMockedEmployee();

        mockedProject.setDepartment(null); 

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(mockedProject));
        Mockito.when(employeeRepository.findAllById(employeeIds)).thenReturn(List.of(mockedEmployee));

        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> {
            projectService.assignEmployeesToProject(projectId, employeeIds);
        });

        Assertions.assertEquals("Project must belong to a department.", exception.getMessage());
    }

    @Test
    public void testRemoveEmployeeFromProject_success() {
        Long projectId = 1L;
        Long employeeId = 1L;
        Project mockedProject = mockProjectService.getMockedProject();
        Employee mockedEmployee = mockProjectService.getMockedEmployee();
        ProjectDTO mockedProjectDTO = mockProjectService.getMockedProjectDTO();

        mockedProject.getAssignedEmployees().add(mockedEmployee);

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(mockedProject));
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(mockedEmployee));
        Mockito.when(projectRepository.save(mockedProject)).thenReturn(mockedProject);
        Mockito.when(projectMapper.fromEntityToProjectDTO(mockedProject)).thenReturn(mockedProjectDTO);

        ProjectDTO result = projectService.removeEmployeeFromProject(projectId, employeeId);

        Assertions.assertNotNull(result);
        verify(projectRepository).save(mockedProject);
    }


    @Test
    public void testRemoveEmployeeFromProject_employeeNotFound_throwsException() {
        Long projectId = 1L;
        Long employeeId = 1L;
        Project mockedProject = mockProjectService.getMockedProject();

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(mockedProject));
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            projectService.removeEmployeeFromProject(projectId, employeeId);
        });

        Assertions.assertEquals("Employee not found with ID: " + employeeId, exception.getMessage());
    }

    @Test
    void testGetAssignedEmployees_success() {
        Long projectId = 1L;
        Project mockProject = mockProjectService.getMockedProject();
        List<EmployeeDTO> mockEmployeeDTOs = mockProjectService.getMockedEmployeeDTOs();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(mockProject));
        when(employeeMapper.fromEntitiesToEmployeeDTOs(mockProject.getAssignedEmployees())).thenReturn(mockEmployeeDTOs);

        List<EmployeeDTO> result = projectService.getAssignedEmployees(projectId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("John Doe", result.get(0).getName());
        Assertions.assertEquals("Mock Department", result.get(0).getDepartmentName());

        verify(projectRepository, times(1)).findById(projectId);
        verify(employeeMapper, times(1)).fromEntitiesToEmployeeDTOs(mockProject.getAssignedEmployees());
    }

    @Test
    void testGetAssignedEmployees_ProjectNotFound() {
        Long projectId = 999L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> projectService.getAssignedEmployees(projectId)
        );

        Assertions.assertEquals("Project not found with ID: 999", exception.getMessage());
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoInteractions(employeeMapper);
    }

    @Test
    public void testGetProjects_success() {
        List<Project> mockedProjects = mockProjectService.getMockedProjects();
        List<ProjectDTO> mockedProjectDTOs = mockProjectService.getMockedProjectDTOs();

        Mockito.when(projectRepository.findAll()).thenReturn(mockedProjects);
        Mockito.when(projectMapper.fromEntitiesToEmployeeDTOs(mockedProjects)).thenReturn(mockedProjectDTOs);

        List<ProjectDTO> result = projectService.getProjects();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        verify(projectRepository).findAll();
    }
}
