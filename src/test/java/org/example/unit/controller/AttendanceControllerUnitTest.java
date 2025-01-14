package org.example.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import org.example.controller.AttendanceController;
import org.example.dto.AttendanceDTO;
import org.example.dto.CreateAttendanceDTO;
import org.example.service.AttendanceService;
import org.example.unit.test_service.MockAttendanceService;
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

import java.time.LocalTime;
import java.util.List;

public class AttendanceControllerUnitTest {

    private final MockAttendanceService mockAttendanceService = new MockAttendanceService();
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AttendanceService attendanceService;

    @InjectMocks
    private AttendanceController attendanceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(attendanceController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    public void testCheckIn_success() throws Exception {
        CreateAttendanceDTO createAttendanceDTO = mockAttendanceService.getMockedCreateAttendanceDTO();
        AttendanceDTO expectedAttendanceDTO = mockAttendanceService.getMockedAttendanceDTOAfterCheckIn();

        String serializedCreateAttendanceDTO = objectMapper.writeValueAsString(createAttendanceDTO);
        String serializedAttendanceDTO = objectMapper.writeValueAsString(expectedAttendanceDTO);

        System.out.println("Serialized CreateAttendanceDTO: " + serializedCreateAttendanceDTO);
        System.out.println("Serialized AttendanceDTO: " + serializedAttendanceDTO);

        Mockito.when(attendanceService.checkIn(Mockito.any(CreateAttendanceDTO.class))).thenReturn(expectedAttendanceDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/attendances/checkin")
                        .contentType("application/json")
                        .content(serializedCreateAttendanceDTO))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(serializedAttendanceDTO));
    }



    @Test
    public void testCheckOut_success() throws Exception {
        Long attendanceId = 1L;
        LocalTime checkOutTime = LocalTime.of(17, 0);
        AttendanceDTO expectedAttendanceDTO = mockAttendanceService.getMockedAttendanceDTO();
        Mockito.when(attendanceService.checkOut(attendanceId, checkOutTime)).thenReturn(expectedAttendanceDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/attendances/checkout")
                        .param("attendanceId", String.valueOf(attendanceId))
                        .param("checkOutTime", checkOutTime.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedAttendanceDTO)));
    }

    @Test
    public void testGetAttendanceRecordsByEmployeeId_success() throws Exception {
        Long employeeId = 1L;
        List<AttendanceDTO> attendanceDTOs = mockAttendanceService.getMockedAttendanceDTOs();
        Mockito.when(attendanceService.getAttendanceRecordsByEmployeeId(employeeId)).thenReturn(attendanceDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/attendances/{employeeId}", employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(attendanceDTOs)));
    }
}
