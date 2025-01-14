package org.example.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class CreateLeaveDTO {
    @NotNull(message = "Employee ID is required!")
    private Long employeeId;

    @NotNull(message = "Start date is required!")
    @Future(message = "The start date must be in the future!")
    private LocalDate startDate;

    @NotNull(message = "End date is required.")
    @Future(message = "The end date must be in the future!")
    private LocalDate endDate;

    @NotBlank(message = "Reason is required.")
    private String reason;

    public @NotNull(message = "Employee ID is required!") Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(@NotNull(message = "Employee ID is required!") Long employeeId) {
        this.employeeId = employeeId;
    }

    public @NotNull(message = "Start date is required!") @Future(message = "The start date must be in the future!") LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull(message = "Start date is required!") @Future(message = "The start date must be in the future!") LocalDate startDate) {
        this.startDate = startDate;
    }

    public @NotNull(message = "End date is required.") @Future(message = "The end date must be in the future!") LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull(message = "End date is required.") @Future(message = "The end date must be in the future!") LocalDate endDate) {
        this.endDate = endDate;
    }

    public @NotBlank(message = "Reason is required.") String getReason() {
        return reason;
    }

    public void setReason(@NotBlank(message = "Reason is required.") String reason) {
        this.reason = reason;
    }
}
