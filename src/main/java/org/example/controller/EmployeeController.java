package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.CreateEmployeeDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.ErrorResponseDTO;
import org.example.dto.UpdateEmployeeDTO;
import org.example.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employee", description = "Operations for managing employee data, including creating, updating, retrieving, and deleting employee records.")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new employee",
            description = "Creates a new employee record with the provided details, including personal information and job-related data.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Employee successfully created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid employee data provided",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public EmployeeDTO createEmployee(@Valid @RequestBody
                                      @Parameter(description = "The employee data required for creating a new employee") CreateEmployeeDTO employee) {
        return employeeService.createEmployee(employee);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve an employee by ID",
            description = "Fetches detailed information about a specific employee by their unique ID, including personal and job-related details.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Employee found and details retrieved",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No employee found with the provided ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public EmployeeDTO getEmployeeById(@PathVariable
                                       @Parameter(description = "Unique ID of the employee to retrieve") Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing employee's details",
            description = "Updates the information of an employee based on their ID. The details provided will overwrite the existing data for that employee.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Employee details successfully updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid employee data provided",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Employee not found with the provided ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public EmployeeDTO updateEmployee(@PathVariable
                                      @Parameter(description = "ID of the employee whose details need to be updated") Long id,
                                      @Valid @RequestBody @Parameter(description = "Updated details of the employee") UpdateEmployeeDTO updatedEmployee) {
        return employeeService.updateEmployee(id, updatedEmployee);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an employee record",
            description = "Deletes the employee record identified by the given ID from the system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Employee record successfully deleted"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Employee not found with the provided ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public void deleteEmployee(@PathVariable
                               @Parameter(description = "ID of the employee to delete") Long id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping
    @Operation(
            summary = "Get a list of all employees",
            description = "Retrieves a comprehensive list of all employees in the system, including their personal and job-related information.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the list of all employees",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public List<EmployeeDTO> getEmployees() {
        return employeeService.getEmployees();
    }
}
