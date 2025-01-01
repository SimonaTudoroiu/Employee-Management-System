package org.example.dto;

import jakarta.validation.constraints.*;

public class CreateDepartmentDTO {
    @NotBlank(message = "Department name is required.")
    private String name;

    private String description;

    public @NotBlank(message = "Department name is required.") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Department name is required.") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
