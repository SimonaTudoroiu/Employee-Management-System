package org.example.dto;

import jakarta.validation.constraints.*;

public class UpdateLeaveDTO {
    @NotBlank(message = "Status is required.")
    private String status;

    public @NotBlank(message = "Status is required.") String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "Status is required.") String status) {
        this.status = status;
    }
}
