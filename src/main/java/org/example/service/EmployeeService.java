package org.example.service;

import org.example.dto.CreateEmployeeDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.UpdateEmployeeDTO;
import org.example.exception.InvalidInputException;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.EmployeeMapper;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeDTO createEmployee(CreateEmployeeDTO createEmployeeDTO) {
        Employee employee = employeeMapper.fromCreateDtoToEntity(createEmployeeDTO);

        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new InvalidInputException("Employee name cannot be empty.");
        }

        Department department = departmentRepository.findById(createEmployeeDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + createEmployeeDTO.getDepartmentId()));

        employee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.fromEntityToEmployeeDTO(savedEmployee);
    }


    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return employeeMapper.fromEntityToEmployeeDTO(employee);
    }

    public EmployeeDTO updateEmployee(Long id, UpdateEmployeeDTO updateEmployeeDTO) {
        Employee existingEmployee = employeeMapper.fromUpdateDtoToEntity(updateEmployeeDTO);
        Employee savedEmployee = employeeRepository.save(existingEmployee);

        return employeeMapper.fromEntityToEmployeeDTO(savedEmployee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<EmployeeDTO> getEmployees() {
        return employeeMapper.fromEntitiesToEmployeeDTOs(employeeRepository.findAll());
    }
}
