package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.CreateDepartmentDTO;
import org.example.dto.DepartmentDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.ErrorResponseDTO;
import org.example.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@Tag(name = "Department", description = "Manage operations related to departments, including creation, retrieval, and deletion of departments, as well as fetching employees within a department.")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new department",
            description = "Creates a new department with the provided details such as name and description.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Department created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartmentDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid department data provided",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    public DepartmentDTO createDepartment(@Valid @RequestBody @Parameter(description = "The details of the department to be created") CreateDepartmentDTO department) {
        return departmentService.createDepartment(department);
    }

    @GetMapping("/{id}/employees")
    @Operation(
            summary = "Get all employees in a department",
            description = "Retrieves a list of employees belonging to the specified department based on the department's ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of employees successfully retrieved",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Department with the specified ID not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public List<EmployeeDTO> getEmployeesByDepartmentId(@PathVariable @Parameter(description = "The ID of the department to retrieve employees for") Long id) {
        return departmentService.getEmployeesByDepartmentId(id);
    }

    @GetMapping
    @Operation(
            summary = "Get all departments",
            description = "Retrieves a complete list of all departments within the organization.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of departments successfully retrieved",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartmentDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public List<DepartmentDTO> getDepartments() {
        return departmentService.getDepartments();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a department",
            description = "Deletes the department identified by the given ID from the system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Department deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Department not found for the provided ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public void deleteDepartment(@PathVariable @Parameter(description = "The ID of the department to delete") Long id) {
        departmentService.deleteDepartment(id);
    }
}
