package org.example.service;

import org.example.dto.CreateReviewDTO;
import org.example.dto.ReviewDTO;
import org.example.mapper.ReviewMapper;
import org.example.model.Employee;
import org.example.model.Review;
import org.example.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addPerformanceReview_ShouldSaveReviewAndReturnDTO() {
        CreateReviewDTO createReviewDTO = new CreateReviewDTO();
        createReviewDTO.setEmployeeId(1L);
        createReviewDTO.setComments("Great performance");
        createReviewDTO.setScore(5);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");

        Review review = new Review();
        review.setId(1L);
        review.setEmployee(employee);
        review.setComments("Great performance");
        review.setScore(5);
        review.setDate(LocalDate.now());

        ReviewDTO expectedDTO = new ReviewDTO();
        expectedDTO.setId(1L);
        expectedDTO.setEmployeeName("John Doe");
        expectedDTO.setComments("Great performance");
        expectedDTO.setScore(5);
        expectedDTO.setDate(LocalDate.now());

        when(reviewMapper.fromCreateDtoToEntity(createReviewDTO)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewMapper.fromEntityToReviewDTO(review)).thenReturn(expectedDTO);

        ReviewDTO actualDTO = reviewService.addPerformanceReview(createReviewDTO);

        assertNotNull(actualDTO);
        assertEquals(expectedDTO.getId(), actualDTO.getId());
        assertEquals(expectedDTO.getEmployeeName(), actualDTO.getEmployeeName());
        assertEquals(expectedDTO.getComments(), actualDTO.getComments());
        assertEquals(expectedDTO.getScore(), actualDTO.getScore());
        assertEquals(expectedDTO.getDate(), actualDTO.getDate());

        verify(reviewMapper).fromCreateDtoToEntity(createReviewDTO);
        verify(reviewRepository).save(review);
        verify(reviewMapper).fromEntityToReviewDTO(review);
    }

    @Test
    void getReviewsByEmployeeId_ShouldReturnListOfReviewDTOs() {
        Long employeeId = 1L;

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");

        Review review1 = new Review();
        review1.setId(1L);
        review1.setEmployee(employee);
        review1.setComments("Excellent work");
        review1.setScore(5);
        review1.setDate(LocalDate.now());

        Review review2 = new Review();
        review2.setId(2L);
        review2.setEmployee(employee);
        review2.setComments("Good effort");
        review2.setScore(4);
        review2.setDate(LocalDate.now());

        ReviewDTO reviewDTO1 = new ReviewDTO();
        reviewDTO1.setId(1L);
        reviewDTO1.setEmployeeName("John Doe");
        reviewDTO1.setComments("Excellent work");
        reviewDTO1.setScore(5);
        reviewDTO1.setDate(LocalDate.now());

        ReviewDTO reviewDTO2 = new ReviewDTO();
        reviewDTO2.setId(2L);
        reviewDTO2.setEmployeeName("John Doe");
        reviewDTO2.setComments("Good effort");
        reviewDTO2.setScore(4);
        reviewDTO2.setDate(LocalDate.now());

        when(reviewRepository.findByEmployeeId(employeeId)).thenReturn(List.of(review1, review2));
        when(reviewMapper.fromEntitiesToReviewDTOs(List.of(review1, review2))).thenReturn(List.of(reviewDTO1, reviewDTO2));

        List<ReviewDTO> actualDTOs = reviewService.getReviewsByEmployeeId(employeeId);

        assertNotNull(actualDTOs);
        assertEquals(2, actualDTOs.size());
        assertEquals(reviewDTO1.getId(), actualDTOs.get(0).getId());
        assertEquals(reviewDTO2.getId(), actualDTOs.get(1).getId());

        verify(reviewRepository).findByEmployeeId(employeeId);
        verify(reviewMapper).fromEntitiesToReviewDTOs(List.of(review1, review2));
    }
}
