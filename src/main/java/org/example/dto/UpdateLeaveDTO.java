package org.example.dto;

import jakarta.validation.constraints.*;
import org.example.model.Leave.LeaveStatus;

public class UpdateLeaveDTO {
    @NotNull(message = "Employee ID is required!")
    @NotBlank(message = "Employee ID cannot be blank!")
    private Long id;

    @NotNull(message = "Status is required!")
    @NotBlank(message = "Status cannot be blank!")
    private LeaveStatus status;

    public UpdateLeaveDTO(LeaveStatus status) {
        this.status = status;
    }

    public @NotNull(message = "Status is required!") @NotBlank(message = "Status cannot be blank!") LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Status is required!") @NotBlank(message = "Status cannot be blank!") LeaveStatus status) {
        this.status = status;
    }

    public @NotNull(message = "Employee ID is required!") @NotBlank(message = "Employee ID cannot be blank!") Long getId() {
        return id;
    }

    public void setId(@NotNull(message = "Employee ID is required!") @NotBlank(message = "Employee ID cannot be blank!") Long id) {
        this.id = id;
    }
}
