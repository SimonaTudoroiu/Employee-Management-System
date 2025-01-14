package org.example.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import org.example.controller.DepartmentController;
import org.example.dto.CreateDepartmentDTO;
import org.example.dto.DepartmentDTO;
import org.example.dto.EmployeeDTO;
import org.example.service.DepartmentService;
import org.example.unit.test_service.MockDepartmentService;
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

public class DepartmentControllerUnitTest {

    private final MockDepartmentService mockDepartmentService = new MockDepartmentService();
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    public void testCreateDepartment_success() throws Exception {
        CreateDepartmentDTO createDepartmentDTO = mockDepartmentService.getMockedCreateDepartmentDTO();
        DepartmentDTO expectedDepartmentDTO = mockDepartmentService.getMockedDepartmentDTO();

        String serializedCreateDepartmentDTO = objectMapper.writeValueAsString(createDepartmentDTO);
        String serializedDepartmentDTO = objectMapper.writeValueAsString(expectedDepartmentDTO);

        Mockito.when(departmentService.createDepartment(Mockito.any(CreateDepartmentDTO.class)))
                .thenReturn(expectedDepartmentDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/departments")
                        .contentType("application/json")
                        .content(serializedCreateDepartmentDTO))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(serializedDepartmentDTO));
    }

    @Test
    public void testGetEmployeesByDepartmentId_success() throws Exception {
        Long departmentId = 1L;
        List<EmployeeDTO> employeeDTOs = mockDepartmentService.getMockedEmployeeDTOs();
        Mockito.when(departmentService.getEmployeesByDepartmentId(departmentId)).thenReturn(employeeDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/departments/{id}/employees", departmentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(employeeDTOs)));
    }

    @Test
    public void testGetDepartments_success() throws Exception {
        List<DepartmentDTO> departmentDTOs = mockDepartmentService.getMockedDepartmentDTOs();
        Mockito.when(departmentService.getDepartments()).thenReturn(departmentDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/departments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(departmentDTOs)));
    }

    @Test
    public void testDeleteDepartment_success() throws Exception {
        Long departmentId = 1L;

        Mockito.doNothing().when(departmentService).deleteDepartment(departmentId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/departments/{id}", departmentId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
