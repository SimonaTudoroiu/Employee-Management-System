package org.example.mapper;

import org.example.dto.CreateProjectDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.ProjectDTO;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Project;
import org.example.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {
    private final DepartmentRepository departmentRepository;

    public ProjectMapper(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Project fromCreateDtoToEntity(CreateProjectDTO createProjectDTO) {
        Project project = new Project();

        project.setName(createProjectDTO.getName());
        project.setDeadline(createProjectDTO.getDeadline());

        Department department = departmentRepository.findById(createProjectDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + createProjectDTO.getDepartmentId()));
        project.setDepartment(department);

        return project;
    }

    public ProjectDTO fromEntityToProjectDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDeadline(project.getDeadline());

        if (project.getDepartment() != null) {
            dto.setDepartmentName(project.getDepartment().getName());
        }

        return dto;
    }

    public List<ProjectDTO> fromEntitiesToEmployeeDTOs(List<Project> projects) {
        return projects.stream()
                .map(this::fromEntityToProjectDTO)
                .collect(Collectors.toList());
    }
}
