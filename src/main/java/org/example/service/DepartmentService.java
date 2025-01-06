package org.example.service;

import org.example.dto.CreateDepartmentDTO;
import org.example.mapper.DepartmentMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public Department createDepartment(CreateDepartmentDTO createDepartmentDTO) {
        Department department = departmentMapper.fromCreateDtoToEntity(createDepartmentDTO);
        return departmentRepository.save(department);
    }

    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        return departmentRepository.findEmployeesByDepartmentId(departmentId);
    }
}
