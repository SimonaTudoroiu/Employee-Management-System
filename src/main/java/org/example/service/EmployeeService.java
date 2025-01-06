package org.example.service;

import org.example.dto.CreateEmployeeDTO;
import org.example.dto.UpdateEmployeeDTO;
import org.example.mapper.EmployeeMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    // Constructor injection for repositories and the mapper
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    // Create employee method using the EmployeeMapper
    public Employee createEmployee(CreateEmployeeDTO createEmployeeDTO) {
        Employee employee = employeeMapper.fromCreateDtoToEntity(createEmployeeDTO);
        return employeeRepository.save(employee);
    }

    // Get employee by ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    // Update employee method using the EmployeeMapper
    public Employee updateEmployee(Long id, UpdateEmployeeDTO updateEmployeeDTO) {
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee = employeeMapper.fromUpdateDtoToEntity(updateEmployeeDTO, existingEmployee);
        return employeeRepository.save(existingEmployee);
    }

    // Delete employee method
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
