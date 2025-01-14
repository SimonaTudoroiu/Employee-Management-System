package org.example.dto;

import jakarta.validation.constraints.*;
import org.example.model.Leave.LeaveStatus;

public class UpdateLeaveDTO {
    @NotNull(message = "Employee ID is required!")
    private Long id;

    @NotNull(message = "Status is required!")
    private LeaveStatus status;

    public UpdateLeaveDTO(LeaveStatus status) {
        this.status = status;
    }

    public @NotNull(message = "Employee ID is required!") Long getId() {
        return id;
    }

    public void setId(@NotNull(message = "Employee ID is required!") Long id) {
        this.id = id;
    }

    public @NotNull(message = "Status is required!") LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Status is required!") LeaveStatus status) {
        this.status = status;
    }
}
