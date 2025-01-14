package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.CreateReviewDTO;
import org.example.dto.ErrorResponseDTO;
import org.example.dto.ReviewDTO;
import org.example.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Review", description = "Operations related to performance reviews, including adding and retrieving reviews for employees.")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @Operation(
            summary = "Submit a performance review for an employee",
            description = "Submit a new performance review for another employee, including feedback on performance and a score from 1 to 5.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Performance review added successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid review data provided",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Employee not found with the provided ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public ReviewDTO addPerformanceReview(@Valid @RequestBody
                                          @Parameter(description = "The details of the performance review being submitted, including feedback and score.") CreateReviewDTO review) {
        return reviewService.addPerformanceReview(review);
    }

    @GetMapping("/{employeeId}")
    @Operation(
            summary = "Retrieve all performance reviews for an employee",
            description = "Fetches the list of all performance reviews for a specific employee based on their unique employee ID, including past and current evaluations.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of performance reviews retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Employee not found for the provided ID",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
                    )

            }
    )
    public List<ReviewDTO> getReviewsByEmployeeId(@PathVariable
                                                  @Parameter(description = "The unique ID of the employee to retrieve performance reviews for") Long employeeId) {
        return reviewService.getReviewsByEmployeeId(employeeId);
    }
}
