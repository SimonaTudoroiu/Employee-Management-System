package org.example.service;

import org.example.dto.CreateReviewDTO;
import org.example.dto.ReviewDTO;
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

    public ReviewDTO addPerformanceReview(CreateReviewDTO createReviewDTO) {
        Review review = reviewMapper.fromCreateDtoToEntity(createReviewDTO);

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.fromEntityToReviewDTO(savedReview);
    }

    public List<ReviewDTO> getReviewsByEmployeeId(Long employeeId) {
        return reviewMapper.fromEntitiesToReviewDTOs(reviewRepository.findByEmployeeId(employeeId));
    }
}
