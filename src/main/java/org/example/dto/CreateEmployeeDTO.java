package org.example.dto;

import jakarta.validation.constraints.*;


public class CreateEmployeeDTO {
    @NotNull(message = "Name is required!")
    @NotBlank(message = "Name cannot be blank!")
    private String name;

    @Email(message = "Invalid email address!")
    @NotNull(message = "Email address is required!")
    private String email;

    @NotNull(message = "Phone number is required!")
    @Pattern(regexp = "^[0-9]*$", message = "Phone number should have only numbers!")
    @Size(min = 10, max = 10, message = "Phone number should have only 10 numbers!")
    private String phone;

    @NotNull(message = "Role is required!")
    private String role;

    @NotNull(message = "Department ID is required!")
    private Long departmentId;

    public @NotNull(message = "Name is required!") @NotBlank(message = "Name cannot be blank!") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Name is required!") @NotBlank(message = "Name cannot be blank!") String name) {
        this.name = name;
    }

    public @Email(message = "Invalid email address!") @NotNull(message = "Email address is required!") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email address!") @NotNull(message = "Email address is required!") String email) {
        this.email = email;
    }

    public @NotNull(message = "Phone number is required!") @Pattern(regexp = "^[0-9]*$", message = "Phone number should have only numbers!") @Size(min = 10, max = 10, message = "Phone number should have only 10 numbers!") String getPhone() {
        return phone;
    }

    public void setPhone(@NotNull(message = "Phone number is required!") @Pattern(regexp = "^[0-9]*$", message = "Phone number should have only numbers!") @Size(min = 10, max = 10, message = "Phone number should have only 10 numbers!") String phone) {
        this.phone = phone;
    }

    public @NotNull(message = "Role is required!") String getRole() {
        return role;
    }

    public void setRole(@NotNull(message = "Role is required!") String role) {
        this.role = role;
    }

    public @NotNull(message = "Department ID is required!") Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(@NotNull(message = "Department ID is required!") Long departmentId) {
        this.departmentId = departmentId;
    }
}
