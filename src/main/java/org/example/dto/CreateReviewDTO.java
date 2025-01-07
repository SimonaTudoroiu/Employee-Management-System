package org.example.dto;

import jakarta.validation.constraints.*;


public class CreateReviewDTO {
    @NotNull(message = "Employee ID is required!")
    @NotBlank(message = "Employee ID cannot be blank!")
    private Long employeeId;

    @NotNull(message = "Comments are required!")
    @NotBlank(message = "Comments cannot be blank!")
    private String comments;

    @NotNull(message = "Score is required!")
    @Min(value = 1, message = "Score must be at least 1!")
    @Max(value = 5, message = "Score must not exceed 5!")
    private int score;

    public @NotNull(message = "Employee ID is required!") @NotBlank(message = "Employee ID cannot be blank!") Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(@NotNull(message = "Employee ID is required!") @NotBlank(message = "Employee ID cannot be blank!") Long employeeId) {
        this.employeeId = employeeId;
    }

    public @NotNull(message = "Comments are required!") @NotBlank(message = "Comments cannot be blank!") String getComments() {
        return comments;
    }

    public void setComments(@NotNull(message = "Comments are required!") @NotBlank(message = "Comments cannot be blank!") String comments) {
        this.comments = comments;
    }

    @NotNull(message = "Score is required!")
    @Min(value = 1, message = "Score must be at least 1!")
    @Max(value = 5, message = "Score must not exceed 5!")
    public int getScore() {
        return score;
    }

    public void setScore(@NotNull(message = "Score is required!") @Min(value = 1, message = "Score must be at least 1!") @Max(value = 5, message = "Score must not exceed 5!") int score) {
        this.score = score;
    }
}
