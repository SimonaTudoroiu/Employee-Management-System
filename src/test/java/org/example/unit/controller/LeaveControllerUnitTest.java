package org.example.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import org.example.controller.LeaveController;
import org.example.dto.CreateLeaveDTO;
import org.example.dto.LeaveDTO;
import org.example.service.LeaveService;
import org.example.unit.test_service.MockLeaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

public class LeaveControllerUnitTest {

    private final MockLeaveService mockLeaveService = new MockLeaveService();
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private LeaveService leaveService;

    @InjectMocks
    private LeaveController leaveController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(leaveController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    public void testApplyForLeave_success() throws Exception {
        CreateLeaveDTO createLeaveDTO = mockLeaveService.getMockedCreateLeaveDTO();
        LeaveDTO expectedLeaveDTO = mockLeaveService.getMockedLeaveDTO();

        String serializedCreateLeaveDTO = objectMapper.writeValueAsString(createLeaveDTO);
        String serializedLeaveDTO = objectMapper.writeValueAsString(expectedLeaveDTO);

        Mockito.when(leaveService.applyForLeave(Mockito.any(CreateLeaveDTO.class))).thenReturn(expectedLeaveDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/leaves")
                        .contentType("application/json")
                        .content(serializedCreateLeaveDTO))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(serializedLeaveDTO));
    }

    @Test
    public void testApproveLeave_success() throws Exception {
        Long leaveId = 1L;
        LeaveDTO expectedLeaveDTO = mockLeaveService.getMockedApprovedLeaveDTO();

        Mockito.when(leaveService.approveLeave(leaveId)).thenReturn(expectedLeaveDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/leaves/{id}/approve", leaveId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedLeaveDTO)));
    }

    @Test
    public void testRejectLeave_success() throws Exception {
        Long leaveId = 1L;
        LeaveDTO expectedLeaveDTO = mockLeaveService.getMockedRejectedLeaveDTO();

        Mockito.when(leaveService.rejectLeave(leaveId)).thenReturn(expectedLeaveDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/leaves/{id}/reject", leaveId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedLeaveDTO)));
    }

    @Test
    public void testGetAllLeavesByDepartmentId_success() throws Exception {
        Long departmentId = 1L;
        List<LeaveDTO> leaveDTOs = mockLeaveService.getMockedLeaveDTOs();

        Mockito.when(leaveService.getAllLeavesByDepartmentId(departmentId)).thenReturn(leaveDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/leaves/{departmentId}", departmentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(leaveDTOs)));
    }
}
