package org.example.controller;

import org.example.dto.CreateProjectDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.ProjectDTO;
import org.example.model.Project;
import org.example.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ProjectDTO createProject(@RequestBody CreateProjectDTO project) {
        return projectService.createProject(project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @PostMapping("/{projectId}/assign")
    public ProjectDTO assignEmployeesToProject(
            @PathVariable Long projectId,
            @RequestBody List<Long> employeeIds) {
        return projectService.assignEmployeesToProject(projectId, employeeIds);
    }

    @GetMapping("/{projectId}/employees")
    public List<EmployeeDTO> getAssignedEmployees(@PathVariable Long projectId) {
        return projectService.getAssignedEmployees(projectId);
    }

    @DeleteMapping("/{projectId}/employees/{employeeId}")
    public ProjectDTO removeEmployeeFromProject(
            @PathVariable Long projectId,
            @PathVariable Long employeeId) {
        return projectService.removeEmployeeFromProject(projectId, employeeId);
    }

    @GetMapping
    public List<ProjectDTO> getProjects() {
        return projectService.getProjects();
    }
}
