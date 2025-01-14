package org.example.service;

import org.example.dto.CreateDepartmentDTO;
import org.example.dto.DepartmentDTO;
import org.example.dto.EmployeeDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.DepartmentMapper;
import org.example.mapper.EmployeeMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final EmployeeMapper employeeMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper, EmployeeMapper employeeMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.employeeMapper = employeeMapper;
    }

    public DepartmentDTO createDepartment(CreateDepartmentDTO createDepartmentDTO) {
        Department department = departmentMapper.fromCreateDtoToEntity(createDepartmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return departmentMapper.fromEntityToDepartmentDTO(savedDepartment);
    }

    public List<EmployeeDTO> getEmployeesByDepartmentId(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));

        return employeeMapper.fromEntitiesToEmployeeDTOs(departmentRepository.findEmployeesByDepartmentId(departmentId));
    }

    public List<DepartmentDTO> getDepartments() {
        return departmentMapper.fromEntitiesToEmployeeDTOs(departmentRepository.findAll());
    }

    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));

        departmentRepository.deleteById(departmentId);
    }
}
