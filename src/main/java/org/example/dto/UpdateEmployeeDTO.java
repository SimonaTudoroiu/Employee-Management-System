package org.example.dto;

import jakarta.validation.constraints.*;

public class UpdateEmployeeDTO {
    @NotBlank(message = "Employee ID cannot be blank!")
    @NotNull(message = "Employee ID is required!")
    private Long id;

    @NotBlank(message = "Phone number cannot be blank!")
    @Pattern(regexp = "^[0-9]*$", message = "Phone number should have only numbers!")
    private String phone;

    @NotBlank(message = "Role cannot be blank!")
    private String role;

    public @NotBlank(message = "Employee ID cannot be blank!") @NotNull(message = "Employee ID is required!") Long getId() {
        return id;
    }

    public void setId(@NotBlank(message = "Employee ID cannot be blank!") @NotNull(message = "Employee ID is required!") Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Phone number cannot be blank!") @Pattern(regexp = "^[0-9]*$", message = "Phone number should have only numbers!") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "Phone number cannot be blank!") @Pattern(regexp = "^[0-9]*$", message = "Phone number should have only numbers!") String phone) {
        this.phone = phone;
    }

    public @NotBlank(message = "Role cannot be blank!") String getRole() {
        return role;
    }

    public void setRole(@NotBlank(message = "Role cannot be blank!") String role) {
        this.role = role;
    }
}
