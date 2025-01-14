package org.example.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.controller.ReviewController;
import org.example.dto.CreateReviewDTO;
import org.example.dto.ReviewDTO;
import org.example.service.ReviewService;
import org.example.unit.test_service.MockReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

public class ReviewControllerUnitTest {

    private final MockReviewService mockReviewService = new MockReviewService();
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    public void testAddPerformanceReview_success() throws Exception {
        CreateReviewDTO createReviewDTO = mockReviewService.getMockedCreateReviewDTO();
        ReviewDTO expectedReviewDTO = mockReviewService.getMockedReviewDTO();

        String serializedCreateReviewDTO = objectMapper.writeValueAsString(createReviewDTO);
        String serializedReviewDTO = objectMapper.writeValueAsString(expectedReviewDTO);

        Mockito.when(reviewService.addPerformanceReview(Mockito.any(CreateReviewDTO.class)))
                .thenReturn(expectedReviewDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/reviews")
                        .contentType("application/json")
                        .content(serializedCreateReviewDTO))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(serializedReviewDTO));
    }

    @Test
    public void testGetReviewsByEmployeeId_success() throws Exception {
        Long employeeId = 1L;
        List<ReviewDTO> reviewDTOs = mockReviewService.getMockedReviewDTOs();
        Mockito.when(reviewService.getReviewsByEmployeeId(employeeId)).thenReturn(reviewDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/{employeeId}", employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(reviewDTOs)));
    }
}
