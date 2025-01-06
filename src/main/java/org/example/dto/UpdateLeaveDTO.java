package org.example.dto;

import jakarta.validation.constraints.*;
import org.example.model.Leave;
import org.example.model.Leave.LeaveStatus;

public class UpdateLeaveDTO {
    @NotBlank(message = "Status is required.")
    private LeaveStatus status;

    public UpdateLeaveDTO(LeaveStatus status) {
        this.status = status;
    }

    public @NotBlank(message = "Status is required.") LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "Status is required.") LeaveStatus status) {
        this.status = status;
    }
}
