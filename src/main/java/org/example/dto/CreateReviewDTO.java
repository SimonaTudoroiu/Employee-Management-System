package org.example.dto;

import jakarta.validation.constraints.*;


public class CreateReviewDTO {
    @NotNull(message = "Employee ID is required.")
    private Long employeeId;

    @NotBlank(message = "Comments are required.")
    private String comments;

    @Min(value = 1, message = "Score must be at least 1.")
    @Max(value = 5, message = "Score must not exceed 5.")
    private int score;

    public @NotNull(message = "Employee ID is required.") Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(@NotNull(message = "Employee ID is required.") Long employeeId) {
        this.employeeId = employeeId;
    }

    public @NotBlank(message = "Comments are required.") String getComments() {
        return comments;
    }

    public void setComments(@NotBlank(message = "Comments are required.") String comments) {
        this.comments = comments;
    }

    @Min(value = 1, message = "Score must be at least 1.")
    @Max(value = 5, message = "Score must not exceed 5.")
    public int getScore() {
        return score;
    }

    public void setScore(@Min(value = 1, message = "Score must be at least 1.") @Max(value = 5, message = "Score must not exceed 5.") int score) {
        this.score = score;
    }
}
