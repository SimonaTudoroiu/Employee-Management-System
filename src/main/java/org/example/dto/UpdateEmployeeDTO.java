package org.example.dto;

import jakarta.validation.constraints.*;

public class UpdateEmployeeDTO {
    @Pattern(regexp = "^[0-9]*$", message = "Phone number should have only numbers!")
    @Size(min = 10, max = 10, message = "Phone number should have only 10 numbers!")

    private String phone;

    private String role;

    public @Pattern(regexp = "^[0-9]*$", message = "Phone number should have only numbers!") @Size(min = 10, max = 10, message = "Phone number should have only 10 numbers!") String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(regexp = "^[0-9]*$", message = "Phone number should have only numbers!") @Size(min = 10, max = 10, message = "Phone number should have only 10 numbers!") String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
