package org.example.service;

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
import org.example.repository.EmployeeRepository;
import org.example.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentRepository departmentRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper, EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, DepartmentRepository departmentRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.departmentRepository = departmentRepository;
    }

    public ProjectDTO createProject(CreateProjectDTO createProjectDTO) {
        Department department = departmentRepository.findById(createProjectDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + createProjectDTO.getDepartmentId()));

        Project project = projectMapper.fromCreateDtoToEntity(createProjectDTO);

        Project savedProject = projectRepository.save(project);

        return projectMapper.fromEntityToProjectDTO(savedProject);
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));

        projectRepository.deleteById(id);
    }

    public ProjectDTO assignEmployeesToProject(Long projectId, List<Long> employeeIds) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        Department projectDepartment = project.getDepartment();
        if (projectDepartment == null) {
            throw new InvalidInputException("Project must belong to a department.");
        }

        List<Employee> employees = new ArrayList<>();

        for(Long id : employeeIds){
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

            employees.add(employee);

        }

        for (Employee employee : employees) {
            if (!employee.getDepartment().getId().equals(projectDepartment.getId())) {
                throw new InvalidInputException("Employee " + employee.getName() + " is not in the same department as the project.");
            }
        }

        project.getAssignedEmployees().addAll(employees);
        Project savedProject = projectRepository.save(project);
        return projectMapper.fromEntityToProjectDTO(savedProject);
    }

    public List<EmployeeDTO> getAssignedEmployees(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
        return employeeMapper.fromEntitiesToEmployeeDTOs(project.getAssignedEmployees());
    }

    public ProjectDTO removeEmployeeFromProject(Long projectId, Long employeeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        if (!project.getAssignedEmployees().remove(employee)) {
            throw new InvalidInputException("Employee is not assigned to this project.");
        }

        Project savedProject = projectRepository.save(project);

        return projectMapper.fromEntityToProjectDTO(savedProject);
    }

    public List<ProjectDTO> getProjects() {
        return projectMapper.fromEntitiesToEmployeeDTOs(projectRepository.findAll());
    }
}
