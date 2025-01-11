package org.example.service;

import org.example.dto.CreateProjectDTO;
import org.example.dto.ProjectDTO;
import org.example.exception.InvalidInputException;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.EmployeeMapper;
import org.example.mapper.ProjectMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Project;
import org.example.repository.EmployeeRepository;
import org.example.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {
    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectRepository = mock(ProjectRepository.class);
        projectMapper = mock(ProjectMapper.class);
        employeeRepository = mock(EmployeeRepository.class);
        employeeMapper = mock(EmployeeMapper.class);
        projectService = new ProjectService(projectRepository, projectMapper, employeeRepository, employeeMapper);
    }

    @Test
    void createProject_ShouldReturnCreatedProjectDTO() {
        CreateProjectDTO createProjectDTO = new CreateProjectDTO();
        createProjectDTO.setName("Project A");
        createProjectDTO.setDeadline(LocalDate.of(2025, 1, 31));
        createProjectDTO.setDepartmentId(1L);

        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        Project project = new Project();
        project.setName("Project A");
        project.setDeadline(LocalDate.of(2025, 1, 31));
        project.setDepartment(department);

        Project savedProject = new Project();
        savedProject.setId(1L);
        savedProject.setName("Project A");
        savedProject.setDeadline(LocalDate.of(2025, 1, 31));
        savedProject.setDepartment(department);

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Project A");
        projectDTO.setDeadline(LocalDate.of(2025, 1, 31));
        projectDTO.setDepartmentName("IT");

        when(projectMapper.fromCreateDtoToEntity(createProjectDTO)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(savedProject);
        when(projectMapper.fromEntityToProjectDTO(savedProject)).thenReturn(projectDTO);

        ProjectDTO result = projectService.createProject(createProjectDTO);

        assertNotNull(result);
        assertEquals("Project A", result.getName());
        assertEquals("IT", result.getDepartmentName());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void deleteProject_ShouldDeleteProject() {
        Long projectId = 1L;

        doNothing().when(projectRepository).deleteById(projectId);

        projectService.deleteProject(projectId);

        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void assignEmployeesToProject_ShouldAssignEmployees() {
        Long projectId = 1L;
        List<Long> employeeIds = List.of(1L, 2L);

        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        Project project = new Project();
        project.setId(1L);
        project.setName("Project A");
        project.setDepartment(department);
        project.setAssignedEmployees(new ArrayList<>());

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("John Doe");
        employee1.setDepartment(department);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane Doe");
        employee2.setDepartment(department);

        Project updatedProject = new Project();
        updatedProject.setId(1L);
        updatedProject.setName("Project A");
        updatedProject.setDepartment(department);
        updatedProject.setAssignedEmployees(List.of(employee1, employee2));

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Project A");
        projectDTO.setDeadline(null);
        projectDTO.setDepartmentName("IT");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(employeeRepository.findAllById(employeeIds)).thenReturn(List.of(employee1, employee2));
        when(projectRepository.save(project)).thenReturn(updatedProject);
        when(projectMapper.fromEntityToProjectDTO(updatedProject)).thenReturn(projectDTO);

        ProjectDTO result = projectService.assignEmployeesToProject(projectId, employeeIds);

        assertNotNull(result);
        assertEquals("Project A", result.getName());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void assignEmployeesToProject_ShouldThrowException_WhenEmployeeNotInSameDepartment() {
        Long projectId = 1L;
        List<Long> employeeIds = List.of(1L);

        Department projectDepartment = new Department();
        projectDepartment.setId(1L);
        projectDepartment.setName("IT");

        Department otherDepartment = new Department();
        otherDepartment.setId(2L);
        otherDepartment.setName("HR");

        Project project = new Project();
        project.setId(1L);
        project.setName("Project A");
        project.setDepartment(projectDepartment);
        project.setAssignedEmployees(new ArrayList<>());

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setDepartment(otherDepartment);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(employeeRepository.findAllById(employeeIds)).thenReturn(List.of(employee));

        InvalidInputException exception = assertThrows(InvalidInputException.class, () ->
                projectService.assignEmployeesToProject(projectId, employeeIds));

        assertEquals("Employee John Doe is not in the same department as the project.", exception.getMessage());
    }

    @Test
    void getAssignedEmployees_ShouldReturnListOfAssignedEmployees() {
        Long projectId = 1L;

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("John Doe");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane Doe");

        Project project = new Project();
        project.setId(1L);
        project.setAssignedEmployees(List.of(employee1, employee2));

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        projectService.getAssignedEmployees(projectId);

        verify(employeeMapper, times(1)).fromEntitiesToEmployeeDTOs(List.of(employee1, employee2));
    }
}
