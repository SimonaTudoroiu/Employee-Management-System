package org.example.service;

import org.example.dto.CreateReviewDTO;
import org.example.mapper.ReviewMapper;
import org.example.model.Review;
import org.example.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    public Review addPerformanceReview(CreateReviewDTO createReviewDTO) {
        Review review = reviewMapper.fromCreateDtoToEntity(createReviewDTO);
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByEmployeeId(Long employeeId) {
        return reviewRepository.findByEmployeeId(employeeId);
    }
}
