package org.example.dto;

import jakarta.validation.constraints.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAttendanceDTO {
    @NotNull(message = "Employee ID is required!")
    @NotBlank(message = "Employee ID cannot be blank!")
    private Long employeeId;
    @NotNull(message = "CheckIn time is required!")
    @NotBlank(message = "CheckIn time cannot be blank!")
    private LocalTime checkInTime;

    public @NotNull(message = "Employee ID is required!") @NotBlank(message = "Employee ID cannot be blank!") Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(@NotNull(message = "Employee ID is required!") @NotBlank(message = "Employee ID cannot be blank!") Long employeeId) {
        this.employeeId = employeeId;
    }

    public @NotNull(message = "CheckIn time is required!") @NotBlank(message = "CheckIn time cannot be blank!") LocalTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(@NotNull(message = "CheckIn time is required!") @NotBlank(message = "CheckIn time cannot be blank!") LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }
}
