package org.example.dto;

import jakarta.validation.constraints.*;


public class CreateEmployeeDTO {
    @NotBlank(message = "Name is required.")
    private String name;

    @Email(message = "Invalid email address.")
    private String email;

    private String phone;

    @NotBlank(message = "Role is required.")
    private String role;

    @NotNull(message = "Department ID is required.")
    private Long departmentId;

    public @NotNull(message = "Department ID is required.") Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(@NotNull(message = "Department ID is required.") Long departmentId) {
        this.departmentId = departmentId;
    }

    public @NotBlank(message = "Role is required.") String getRole() {
        return role;
    }

    public void setRole(@NotBlank(message = "Role is required.") String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public @Email(message = "Invalid email address.") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email address.") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Name is required.") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required.") String name) {
        this.name = name;
    }
}
