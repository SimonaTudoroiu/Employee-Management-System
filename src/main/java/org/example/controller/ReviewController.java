package org.example.controller;

import org.example.dto.CreateReviewDTO;
import org.example.dto.ReviewDTO;
import org.example.model.Review;
import org.example.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewDTO addPerformanceReview(@RequestBody CreateReviewDTO review) {
        return reviewService.addPerformanceReview(review);
    }

    @GetMapping("/{employeeId}")
    public List<ReviewDTO> getReviewsByEmployeeId(@PathVariable Long employeeId) {
        return reviewService.getReviewsByEmployeeId(employeeId);
    }
}
