package org.example.service;

import org.example.dto.CreateReviewDTO;
import org.example.dto.ReviewDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.ReviewMapper;
import org.example.model.Employee;
import org.example.model.Review;
import org.example.repository.EmployeeRepository;
import org.example.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final EmployeeRepository employeeRepository;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper, EmployeeRepository employeeRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.employeeRepository = employeeRepository;
    }

    public ReviewDTO addPerformanceReview(CreateReviewDTO createReviewDTO) {
        Employee employee = employeeRepository.findById(createReviewDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with the id: " + createReviewDTO.getEmployeeId()));

        Review review = reviewMapper.fromCreateDtoToEntity(createReviewDTO);

        review.setEmployee(employee);

        Review savedReview = reviewRepository.save(review);

        return reviewMapper.fromEntityToReviewDTO(savedReview);
    }


    public List<ReviewDTO> getReviewsByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with the id: " +employeeId));

        return reviewMapper.fromEntitiesToReviewDTOs(reviewRepository.findByEmployeeId(employeeId));
    }
}
