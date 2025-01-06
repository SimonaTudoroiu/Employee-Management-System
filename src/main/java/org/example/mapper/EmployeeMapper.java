package org.example.mapper;

import org.example.dto.CreateEmployeeDTO;
import org.example.dto.UpdateEmployeeDTO;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeMapper {
    private final DepartmentRepository departmentRepository;

    // Injecting the DepartmentRepository to fetch the Department based on ID for CreateEmployeeDTO
    public EmployeeMapper(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // Converts CreateEmployeeDTO to Employee entity (for creating a new employee)
    public Employee fromCreateDtoToEntity(CreateEmployeeDTO createEmployeeDTO) {
        Employee employee = new Employee();
        employee.setName(createEmployeeDTO.getName());
        employee.setEmail(createEmployeeDTO.getEmail());
        employee.setPhone(createEmployeeDTO.getPhone());
        employee.setRole(createEmployeeDTO.getRole());
        employee.setJoinDate(LocalDate.now());  // Set the join date to current date

        // Fetch the department using the department ID from DTO and set it on the employee
        Department department = departmentRepository.findById(createEmployeeDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        employee.setDepartment(department);

        return employee;
    }

    // Converts UpdateEmployeeDTO to Employee entity (for updating an existing employee)
    public Employee fromUpdateDtoToEntity(UpdateEmployeeDTO updateEmployeeDTO, Employee existingEmployee) {
        if (updateEmployeeDTO.getPhone() != null) {
            existingEmployee.setPhone(updateEmployeeDTO.getPhone());
        }
        if (updateEmployeeDTO.getRole() != null) {
            existingEmployee.setRole(updateEmployeeDTO.getRole());
        }
        return existingEmployee;
    }

    // Converts Employee entity to CreateEmployeeDTO (usually not needed, but can be useful for response purposes)
    public CreateEmployeeDTO fromEntityToCreateDto(Employee employee) {
        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO();
        createEmployeeDTO.setName(employee.getName());
        createEmployeeDTO.setEmail(employee.getEmail());
        createEmployeeDTO.setPhone(employee.getPhone());
        createEmployeeDTO.setRole(employee.getRole());
        createEmployeeDTO.setDepartmentId(employee.getDepartment().getId()); // Return the department ID
        return createEmployeeDTO;
    }

    // Converts Employee entity to UpdateEmployeeDTO (usually for partial updates)
    public UpdateEmployeeDTO fromEntityToUpdateDto(Employee employee) {
        UpdateEmployeeDTO updateEmployeeDTO = new UpdateEmployeeDTO();
        updateEmployeeDTO.setPhone(employee.getPhone());
        updateEmployeeDTO.setRole(employee.getRole());
        return updateEmployeeDTO;
    }
}
