package org.example.mapper;

import org.example.dto.CreateEmployeeDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.UpdateEmployeeDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeMapper(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public Employee fromCreateDtoToEntity(CreateEmployeeDTO createEmployeeDTO) {
        Employee employee = new Employee();
        employee.setName(createEmployeeDTO.getName());
        employee.setEmail(createEmployeeDTO.getEmail());
        employee.setPhone(createEmployeeDTO.getPhone());
        employee.setRole(createEmployeeDTO.getRole());
        employee.setJoinDate(LocalDate.now());

        Department department = departmentRepository.findById(createEmployeeDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        employee.setDepartment(department);

        return employee;
    }

    public Employee fromUpdateDtoToEntity(UpdateEmployeeDTO updateEmployeeDTO) {
        Employee existingEmployee = employeeRepository.findById(updateEmployeeDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + updateEmployeeDTO.getId()));
        if (updateEmployeeDTO.getPhone() != null) {
            existingEmployee.setPhone(updateEmployeeDTO.getPhone());
        }
        if (updateEmployeeDTO.getRole() != null) {
            existingEmployee.setRole(updateEmployeeDTO.getRole());
        }
        return existingEmployee;
    }

    public EmployeeDTO fromEntityToEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setRole(employee.getRole());
        dto.setDepartmentName(employee.getDepartment() != null ? employee.getDepartment().getName() : null);
        dto.setJoinDate(employee.getJoinDate());

        return dto;
    }

    public List<EmployeeDTO> fromEntitiesToEmployeeDTOs(List<Employee> employees) {
        return employees.stream()
                .map(this::fromEntityToEmployeeDTO)
                .collect(Collectors.toList());
    }
}
