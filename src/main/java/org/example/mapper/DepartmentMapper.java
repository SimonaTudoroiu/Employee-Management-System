package org.example.mapper;

import org.example.dto.CreateDepartmentDTO;
import org.example.dto.DepartmentDTO;
import org.example.dto.EmployeeDTO;
import org.example.model.Department;
import org.example.model.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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


    public DepartmentDTO fromEntityToDepartmentDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());

        return dto;
    }

    public List<DepartmentDTO> fromEntitiesToEmployeeDTOs(List<Department> departments) {
        return departments.stream()
                .map(this::fromEntityToDepartmentDTO)
                .collect(Collectors.toList());
    }
}
