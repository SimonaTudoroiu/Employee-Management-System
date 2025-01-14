package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.AttendanceDTO;
import org.example.dto.CreateAttendanceDTO;
import org.example.dto.ErrorResponseDTO;
import org.example.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/attendances")
@Tag(name = "Attendance", description = "Manage employee attendance records, including check-ins, check-outs, and retrieval of attendance history.")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Operation(
            summary = "Record employee check-in",
            description = "Records the check-in time for an employee when they arrive at work, logging their attendance.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Check-in successfully recorded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid attendance data provided",
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
    @PostMapping("/checkin")
    public AttendanceDTO checkIn(@Valid @RequestBody @Parameter(description = "Attendance details for employee check-in") CreateAttendanceDTO attendance) {
        return attendanceService.checkIn(attendance);
    }

    @Operation(
            summary = "Record employee check-out",
            description = "Records the check-out time for an employee when they leave work, marking the end of their attendance for the day.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Check-out successfully recorded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Attendance record not found with the provided ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    @PostMapping("/checkout")
    public AttendanceDTO checkOut(
            @RequestParam @Parameter(description = "Attendance record ID for check-out") Long attendanceId,
            @RequestParam @Parameter(description = "Employee's check-out time") LocalTime checkOutTime
    ) {
        return attendanceService.checkOut(attendanceId, checkOutTime);
    }

    @Operation(
            summary = "Retrieve employee attendance records",
            description = "Fetches all attendance records for a specific employee by their ID, including check-in and check-out times.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attendance records successfully retrieved",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class))
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
    @GetMapping("/{employeeId}")
    public List<AttendanceDTO> getAttendanceRecordsByEmployeeId(
            @PathVariable @Parameter(description = "Employee ID to retrieve attendance records") Long employeeId
    ) {
        return attendanceService.getAttendanceRecordsByEmployeeId(employeeId);
    }
}
