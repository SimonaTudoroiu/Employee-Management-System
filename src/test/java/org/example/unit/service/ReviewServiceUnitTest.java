package org.example.unit.service;

import org.example.dto.CreateReviewDTO;
import org.example.dto.ReviewDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.ReviewMapper;
import org.example.model.Employee;
import org.example.model.Review;
import org.example.repository.ReviewRepository;
import org.example.repository.EmployeeRepository;
import org.example.service.ReviewService;
import org.example.unit.test_service.MockReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ReviewServiceUnitTest {

    private MockReviewService mockReviewService = new MockReviewService();

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddPerformanceReview_success() {
        CreateReviewDTO createReviewDTO = mockReviewService.getMockedCreateReviewDTO();
        Review mockedReview = mockReviewService.getMockedReview();
        Employee mockedEmployee = mockReviewService.getMockedEmployee();
        ReviewDTO mockedReviewDTO = mockReviewService.getMockedReviewDTO();

        Mockito.when(employeeRepository.findById(mockedEmployee.getId())).thenReturn(Optional.of(mockedEmployee));
        Mockito.when(reviewMapper.fromCreateDtoToEntity(createReviewDTO)).thenReturn(mockedReview);
        Mockito.when(reviewRepository.save(any(Review.class))).thenReturn(mockedReview);
        Mockito.when(reviewMapper.fromEntityToReviewDTO(mockedReview)).thenReturn(mockedReviewDTO);

        ReviewDTO result = reviewService.addPerformanceReview(createReviewDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockedReviewDTO, result);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    public void testAddPerformanceReview_employeeNotFound_throwsException() {
        CreateReviewDTO createReviewDTO = mockReviewService.getMockedCreateReviewDTO();

        Mockito.when(employeeRepository.findById(createReviewDTO.getEmployeeId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.addPerformanceReview(createReviewDTO);
        });

        Assertions.assertEquals("Employee not found with the id: " + createReviewDTO.getEmployeeId(), exception.getMessage());
        verify(reviewRepository, never()).save(any(Review.class));
    }


    @Test
    public void testGetReviewsByEmployeeId_success() {
        Long employeeId = 1L;

        Employee mockedEmployee = new Employee();
        mockedEmployee.setId(employeeId);

        List<Review> mockedReviews = mockReviewService.getMockedReviews();
        List<ReviewDTO> mockedReviewDTOs = mockReviewService.getMockedReviewDTOs();

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(mockedEmployee));

        Mockito.when(reviewRepository.findByEmployeeId(employeeId)).thenReturn(mockedReviews);
        Mockito.when(reviewMapper.fromEntitiesToReviewDTOs(mockedReviews)).thenReturn(mockedReviewDTOs);

        List<ReviewDTO> result = reviewService.getReviewsByEmployeeId(employeeId);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(mockedReviewDTOs, result);

        Mockito.verify(employeeRepository).findById(employeeId);
        Mockito.verify(reviewRepository).findByEmployeeId(employeeId);
    }

    @Test
    public void testGetReviewsByEmployeeId_noReviewsFound_returnsEmptyList() {
        Long employeeId = 1L;

        // Mocking Employee existence
        Employee mockedEmployee = new Employee();
        mockedEmployee.setId(employeeId);

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(mockedEmployee));

        // Mocking no reviews found
        Mockito.when(reviewRepository.findByEmployeeId(employeeId)).thenReturn(List.of());

        // Call the method
        List<ReviewDTO> result = reviewService.getReviewsByEmployeeId(employeeId);

        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());

        // Verifications
        verify(employeeRepository).findById(employeeId);
        verify(reviewRepository).findByEmployeeId(employeeId);
    }

}
