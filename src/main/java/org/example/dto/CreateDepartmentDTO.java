package org.example.dto;

import jakarta.validation.constraints.*;

public class CreateDepartmentDTO {
    @NotNull(message = "Department name is required!")
    @NotBlank(message = "Department name cannot be blank!")
    private String name;

    private String description;

    public @NotNull(message = "Department name is required!") @NotBlank(message = "Department name cannot be blank!") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Department name is required!") @NotBlank(message = "Department name cannot be blank!") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
