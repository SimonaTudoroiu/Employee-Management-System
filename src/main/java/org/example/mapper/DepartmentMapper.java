package org.example.mapper;

import org.example.dto.CreateDepartmentDTO;
import org.example.model.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public Department fromCreateDtoToEntity(CreateDepartmentDTO createDepartmentDTO) {
        if (createDepartmentDTO == null) {
            return null;
        }

        Department department = new Department();
        department.setName(createDepartmentDTO.getName());
        department.setDescription(createDepartmentDTO.getDescription());

        return department;
    }

    public CreateDepartmentDTO fromEntityToCreateDto(Department department) {
        if (department == null) {
            return null;
        }

        CreateDepartmentDTO createDepartmentDTO = new CreateDepartmentDTO();
        createDepartmentDTO.setName(department.getName());
        createDepartmentDTO.setDescription(department.getDescription());

        return createDepartmentDTO;
    }
}
