package org.example.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class CreateAttendanceDTO {
    @NotNull(message = "Employee ID is required.")
    private Long employeeId;

    @NotNull(message = "Date is required.")
    private LocalDate date;

    private String checkInTime;
    private String checkOutTime;

    public @NotNull(message = "Employee ID is required.") Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(@NotNull(message = "Employee ID is required.") Long employeeId) {
        this.employeeId = employeeId;
    }

    public @NotNull(message = "Date is required.") LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull(message = "Date is required.") LocalDate date) {
        this.date = date;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
}
