package org.example.dto;

import jakarta.validation.constraints.*;

public class UpdateEmployeeDTO {
    private String phone;

    @NotBlank(message = "Role is required.")
    private String role;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public @NotBlank(message = "Role is required.") String getRole() {
        return role;
    }

    public void setRole(@NotBlank(message = "Role is required.") String role) {
        this.role = role;
    }
}
