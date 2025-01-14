package org.example.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.controller.ProjectController;
import org.example.dto.CreateProjectDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.ProjectDTO;
import org.example.service.ProjectService;
import org.example.unit.test_service.MockProjectService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class ProjectControllerUnitTest {

    private final MockProjectService mockProjectService = new MockProjectService();
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(projectController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    public void testCreateProject_success() throws Exception {
        CreateProjectDTO createProjectDTO = mockProjectService.getMockedCreateProjectDTO();
        ProjectDTO expectedProjectDTO = mockProjectService.getMockedProjectDTO();

        Mockito.when(projectService.createProject(any(CreateProjectDTO.class))).thenReturn(expectedProjectDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/projects")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createProjectDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedProjectDTO)));
    }

    @Test
    public void testDeleteProject_success() throws Exception {
        Long projectId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/projects/{id}", projectId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(projectService).deleteProject(projectId);
    }

    @Test
    public void testAssignEmployeesToProject_success() throws Exception {
        Long projectId = 1L;
        List<Long> employeeIds = List.of(1L);
        ProjectDTO expectedProjectDTO = mockProjectService.getMockedProjectDTO();

        Mockito.when(projectService.assignEmployeesToProject(eq(projectId), any(List.class))).thenReturn(expectedProjectDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/projects/{projectId}/assign", projectId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeIds)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedProjectDTO)));
    }

    @Test
    public void testGetAssignedEmployees_success() throws Exception {
        Long projectId = 1L;
        List<EmployeeDTO> expectedEmployeeDTOs = mockProjectService.getMockedEmployeeDTOs();

        Mockito.when(projectService.getAssignedEmployees(projectId)).thenReturn(expectedEmployeeDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/employees", projectId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedEmployeeDTOs)));
    }

    @Test
    public void testRemoveEmployeeFromProject_success() throws Exception {
        Long projectId = 1L;
        Long employeeId = 2L;
        ProjectDTO expectedProjectDTO = mockProjectService.getMockedProjectDTO();

        Mockito.when(projectService.removeEmployeeFromProject(projectId, employeeId)).thenReturn(expectedProjectDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/projects/{projectId}/employees/{employeeId}", projectId, employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedProjectDTO)));
    }

    @Test
    public void testGetProjects_success() throws Exception {
        List<ProjectDTO> expectedProjectDTOs = mockProjectService.getMockedProjectDTOs();

        Mockito.when(projectService.getProjects()).thenReturn(expectedProjectDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedProjectDTOs)));
    }
}
