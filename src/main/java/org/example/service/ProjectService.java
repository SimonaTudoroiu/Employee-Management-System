package org.example.service;

import org.example.dto.CreateProjectDTO;
import org.example.mapper.ProjectMapper;
import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public Project createProject(CreateProjectDTO createProjectDTO) {
        Project project = projectMapper.fromCreateDtoToEntity(createProjectDTO);

        return projectRepository.save(project);
    }
}
