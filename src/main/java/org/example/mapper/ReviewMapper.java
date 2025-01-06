package org.example.mapper;

import org.example.dto.CreateReviewDTO;
import org.example.model.Employee;
import org.example.model.Review;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    private final EmployeeRepository employeeRepository;

    public ReviewMapper(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Review fromCreateDtoToEntity(CreateReviewDTO createReviewDTO) {
        Employee employee = employeeRepository.findById(createReviewDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Review review = new Review();
        review.setEmployee(employee);
        review.setComments(createReviewDTO.getComments());
        review.setScore(createReviewDTO.getScore());
        review.setDate(java.time.LocalDate.now());

        return review;
    }
}
