package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.CreateLeaveDTO;
import org.example.dto.ErrorResponseDTO;
import org.example.dto.LeaveDTO;
import org.example.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
@Tag(name = "Leave", description = "Operations related to managing leave requests, including applying, approving, rejecting, and retrieving leave data.")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    @Operation(
            summary = "Submit a new leave request",
            description = "Allows employees to submit a new leave request with the provided leave details such as start date, end date, and reason for leave.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Leave request submitted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeaveDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid leave data provided",
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
    public LeaveDTO applyForLeave(@Valid @RequestBody
                                  @Parameter(description = "Details of the leave request including dates and reason") CreateLeaveDTO leave) {
        return leaveService.applyForLeave(leave);
    }

    @PutMapping("/{id}/approve")
    @Operation(
            summary = "Approve a leave request",
            description = "Approves the leave request for the employee identified by the provided leave request ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Leave request approved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeaveDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Leave request not found for the given ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public LeaveDTO approveLeave(@PathVariable
                                 @Parameter(description = "ID of the leave request to approve") Long id) {
        return leaveService.approveLeave(id);
    }

    @PutMapping("/{id}/reject")
    @Operation(
            summary = "Reject a leave request",
            description = "Rejects the leave request for the employee identified by the provided leave request ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Leave request rejected successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeaveDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Leave request not found for the given ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public LeaveDTO rejectLeave(@PathVariable
                                @Parameter(description = "ID of the leave request to reject") Long id) {
        return leaveService.rejectLeave(id);
    }

    @GetMapping("/{departmentId}")
    @Operation(
            summary = "Get all leave requests by department",
            description = "Retrieves all leave requests submitted by employees within a specific department, identified by the department ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of leave requests retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeaveDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Department not found for the given ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public List<LeaveDTO> getAllLeavesByDepartmentId(@PathVariable
                                                     @Parameter(description = "ID of the department to retrieve leave requests for") Long departmentId) {
        return leaveService.getAllLeavesByDepartmentId(departmentId);
    }
}
