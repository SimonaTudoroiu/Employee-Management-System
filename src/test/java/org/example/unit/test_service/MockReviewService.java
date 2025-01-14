package org.example.unit.test_service;

import org.example.dto.CreateReviewDTO;
import org.example.dto.ReviewDTO;
import org.example.model.Employee;
import org.example.model.Review;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MockReviewService {

    public Review getMockedReview() {
        Review review = new Review();
        review.setId(1L);
        review.setEmployee(getMockedEmployee());
        review.setDate(LocalDate.now());
        review.setComments("Excellent performance on the project.");
        review.setScore(5);
        return review;
    }

    public Employee getMockedEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        return employee;
    }

    public CreateReviewDTO getMockedCreateReviewDTO() {
        CreateReviewDTO createReviewDTO = new CreateReviewDTO();
        createReviewDTO.setEmployeeId(1L);
        createReviewDTO.setComments("Great teamwork and dedication.");
        createReviewDTO.setScore(4);
        return createReviewDTO;
    }

    public ReviewDTO getMockedReviewDTO() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(1L);
        reviewDTO.setEmployeeName("John Doe");
        reviewDTO.setDate(LocalDate.now());
        reviewDTO.setComments("Excellent performance on the project.");
        reviewDTO.setScore(5);
        return reviewDTO;
    }

    public List<Review> getMockedReviews() {
        return List.of(getMockedReview());
    }

    public List<ReviewDTO> getMockedReviewDTOs() {
        return List.of(getMockedReviewDTO());
    }
}
