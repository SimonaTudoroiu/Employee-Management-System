package org.example.unit.test_service;

import org.example.dto.CreateProjectDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.ProjectDTO;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Project;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MockProjectService {

    public Project getMockedProject() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Mock Project");
        project.setDeadline(LocalDate.of(2025, 12, 31));
        project.setDepartment(getMockedDepartment());
        project.setAssignedEmployees(new ArrayList<>(List.of(getMockedEmployee())));
        return project;
    }

    public ProjectDTO getMockedProjectDTO() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Mock Project");
        projectDTO.setDeadline(LocalDate.of(2025, 12, 31));
        projectDTO.setDepartmentName("Mock Department");
        return projectDTO;
    }

    public ProjectDTO getMockedProjectDTOWithNoEmployees() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Mock Project");
        projectDTO.setDeadline(LocalDate.of(2025, 12, 31));
        projectDTO.setDepartmentName("Mock Department");
        return projectDTO;
    }

    public CreateProjectDTO getMockedCreateProjectDTO() {
        CreateProjectDTO createProjectDTO = new CreateProjectDTO();
        createProjectDTO.setName("Mock Project");
        createProjectDTO.setDeadline(LocalDate.of(2025, 12, 31));
        createProjectDTO.setDepartmentId(1L);
        return createProjectDTO;
    }

    public Department getMockedDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Mock Department");
        return department;
    }

    public Employee getMockedEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setDepartment(getMockedDepartment());
        return employee;
    }

    public List<Employee> getMockedEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(getMockedEmployee()); 
        return employees;
    }

    public List<EmployeeDTO> getMockedEmployeeDTOs() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        employeeDTO.setDepartmentName("Mock Department");
        return List.of(employeeDTO);
    }

    public List<Project> getMockedProjects() {
        return List.of(getMockedProject());
    }

    public List<ProjectDTO> getMockedProjectDTOs() {
        return List.of(getMockedProjectDTO());
    }
}
