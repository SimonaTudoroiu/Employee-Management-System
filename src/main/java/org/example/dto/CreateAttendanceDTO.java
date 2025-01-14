package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAttendanceDTO {
    @NotNull(message = "Employee ID is required!")
    private Long employeeId;
    @NotNull(message = "CheckIn time is required!")
    @Schema(type = "string", format = "time", example = "01:01:01")
    private LocalTime checkInTime;

    public @NotNull(message = "Employee ID is required!") Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(@NotNull(message = "Employee ID is required!") Long employeeId) {
        this.employeeId = employeeId;
    }

    public @NotNull(message = "CheckIn time is required!") LocalTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(@NotNull(message = "CheckIn time is required!") LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }
}
