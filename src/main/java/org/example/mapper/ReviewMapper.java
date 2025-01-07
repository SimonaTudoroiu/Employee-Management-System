package org.example.mapper;

import org.example.dto.CreateReviewDTO;
import org.example.dto.ReviewDTO;
import org.example.model.Employee;
import org.example.model.Review;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public ReviewDTO fromEntityToReviewDTO(Review review) {
        if (review == null) {
            return null;
        }

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setEmployeeName(review.getEmployee().getName());
        reviewDTO.setDate(review.getDate());
        reviewDTO.setComments(review.getComments());
        reviewDTO.setScore(review.getScore());

        return reviewDTO;
    }

    public List<ReviewDTO> fromEntitiesToReviewDTOs(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return List.of();
        }

        return reviews.stream()
                .map(this::fromEntityToReviewDTO)
                .collect(Collectors.toList());
    }

}
