package org.example.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class CreateProjectDTO {
    @NotBlank(message = "Project name cannot be blank!")
    @NotNull(message = "Project name is required!")
    private String name;

    @NotNull(message = "Deadline is required!")
    @Future(message = "The deadline must be in the future!")
    private LocalDate deadline;

    @NotBlank(message = "Department ID cannot be blank!")
    @NotNull(message = "Department ID is required!")
    private Long departmentId;

    public @NotBlank(message = "Project name cannot be blank!") @NotNull(message = "Project name is required!") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Project name cannot be blank!") @NotNull(message = "Project name is required!") String name) {
        this.name = name;
    }

    public @NotNull(message = "Deadline is required!") @Future(message = "The deadline must be in the future!") LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(@NotNull(message = "Deadline is required!") @Future(message = "The deadline must be in the future!") LocalDate deadline) {
        this.deadline = deadline;
    }

    public @NotBlank(message = "Department ID cannot be blank!") @NotNull(message = "Department ID is required!") Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(@NotBlank(message = "Department ID cannot be blank!") @NotNull(message = "Department ID is required!") Long departmentId) {
        this.departmentId = departmentId;
    }
}
