package org.example.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import org.example.controller.EmployeeController;
import org.example.dto.CreateEmployeeDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.UpdateEmployeeDTO;
import org.example.service.EmployeeService;
import org.example.unit.test_service.MockEmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

public class EmployeeControllerUnitTest {

    private final MockEmployeeService mockEmployeeService = new MockEmployeeService();
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    public void testCreateEmployee_success() throws Exception {
        CreateEmployeeDTO createEmployeeDTO = mockEmployeeService.getMockedCreateEmployeeDTO();
        EmployeeDTO expectedEmployeeDTO = mockEmployeeService.getMockedEmployeeDTO();

        String serializedCreateEmployeeDTO = objectMapper.writeValueAsString(createEmployeeDTO);
        String serializedEmployeeDTO = objectMapper.writeValueAsString(expectedEmployeeDTO);

        Mockito.when(employeeService.createEmployee(Mockito.any(CreateEmployeeDTO.class)))
                .thenReturn(expectedEmployeeDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType("application/json")
                        .content(serializedCreateEmployeeDTO))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(serializedEmployeeDTO));
    }

    @Test
    public void testGetEmployeeById_success() throws Exception {
        Long employeeId = 1L;
        EmployeeDTO expectedEmployeeDTO = mockEmployeeService.getMockedEmployeeDTO();
        Mockito.when(employeeService.getEmployeeById(employeeId)).thenReturn(expectedEmployeeDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedEmployeeDTO)));
    }

    @Test
    public void testUpdateEmployee_success() throws Exception {
        Long employeeId = 1L;
        UpdateEmployeeDTO updateEmployeeDTO = mockEmployeeService.getMockedUpdateEmployeeDTO();
        EmployeeDTO expectedEmployeeDTO = mockEmployeeService.getMockedEmployeeDTO();

        String serializedUpdateEmployeeDTO = objectMapper.writeValueAsString(updateEmployeeDTO);
        String serializedEmployeeDTO = objectMapper.writeValueAsString(expectedEmployeeDTO);

        Mockito.when(employeeService.updateEmployee(Mockito.anyLong(), Mockito.any(UpdateEmployeeDTO.class)))
                .thenReturn(expectedEmployeeDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", employeeId)
                        .contentType("application/json")
                        .content(serializedUpdateEmployeeDTO))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(serializedEmployeeDTO));
    }

    @Test
    public void testDeleteEmployee_success() throws Exception {
        Long employeeId = 1L;

        Mockito.doNothing().when(employeeService).deleteEmployee(employeeId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetEmployees_success() throws Exception {
        List<EmployeeDTO> employeeDTOs = mockEmployeeService.getMockedEmployeeDTOs();
        Mockito.when(employeeService.getEmployees()).thenReturn(employeeDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(employeeDTOs)));
    }
}
