package org.example.mapper;

import org.example.dto.CreateProjectDTO;
import org.example.model.Department;
import org.example.model.Project;
import org.example.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

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
}
