package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.CreateProjectDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.ErrorResponseDTO;
import org.example.dto.ProjectDTO;
import org.example.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Tag(name = "Project", description = "Operations related to managing projects, including creation, deletion, employee assignment, and project retrieval.")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new project",
            description = "Allows users to create a new project by providing necessary details such as project name, deadline and associated department",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Project successfully created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid project data provided",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Department not found for the given ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred during input validation",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public ProjectDTO createProject(@Valid @RequestBody
                                    @Parameter(description = "Details required to create a new project, including project name, description, etc.") CreateProjectDTO project) {
        return projectService.createProject(project);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a project",
            description = "Deletes a specific project by its ID, removing all associated data.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Project deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project not found for the given ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred during input validation",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public void deleteProject(@PathVariable
                              @Parameter(description = "ID of the project to be deleted") Long id) {
        projectService.deleteProject(id);
    }

    @PostMapping("/{projectId}/assign")
    @Operation(
            summary = "Assign employees to a project",
            description = "Assigns a list of employees to a specific project, linking them to the project by their employee IDs.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Employees successfully assigned to the project",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project or one or more employees not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "One or more employees not in the same department as the project",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred during input validation",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public ProjectDTO assignEmployeesToProject(
            @PathVariable
            @Parameter(description = "ID of the project to which employees will be assigned") Long projectId,
            @Valid @RequestBody
            @Parameter(description = "List of employee IDs to assign to the project") List<Long> employeeIds) {
        return projectService.assignEmployeesToProject(projectId, employeeIds);
    }

    @GetMapping("/{projectId}/employees")
    @Operation(
            summary = "Get employees assigned to a project",
            description = "Retrieves a list of employees who are currently assigned to a specific project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of employees retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project not found for the given ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred during input validation",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public List<EmployeeDTO> getAssignedEmployees(@PathVariable
                                                  @Parameter(description = "ID of the project to retrieve assigned employees for") Long projectId) {
        return projectService.getAssignedEmployees(projectId);
    }

    @DeleteMapping("/{projectId}/employees/{employeeId}")
    @Operation(
            summary = "Remove an employee from a project",
            description = "Removes a specific employee from a project using both the project ID and the employee ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Employee successfully removed from the project",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project or employee not found for the given IDs",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred during input validation",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public ProjectDTO removeEmployeeFromProject(
            @PathVariable
            @Parameter(description = "ID of the project from which to remove the employee") Long projectId,
            @PathVariable
            @Parameter(description = "ID of the employee to be removed from the project") Long employeeId) {
        return projectService.removeEmployeeFromProject(projectId, employeeId);
    }

    @GetMapping
    @Operation(
            summary = "Get all projects",
            description = "Retrieves a list of all projects in the system, including key details such as project name, deadline and associated department.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of projects retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred during input validation",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public List<ProjectDTO> getProjects() {
        return projectService.getProjects();
    }
}
