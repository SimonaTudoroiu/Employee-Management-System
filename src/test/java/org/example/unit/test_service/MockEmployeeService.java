package org.example.unit.test_service;

import org.example.dto.CreateEmployeeDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.UpdateEmployeeDTO;
import org.example.model.Employee;
import org.example.model.Department;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MockEmployeeService {

    public Employee getMockedEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setEmail("john.doe@example.com");
        employee.setPhone("1234567890");
        employee.setRole("Developer");
        employee.setJoinDate(LocalDate.now());
        employee.setDepartment(getMockedDepartment());
        return employee;
    }

    public Department getMockedDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Engineering");
        return department;
    }

    public CreateEmployeeDTO getMockedCreateEmployeeDTO() {
        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO();
        createEmployeeDTO.setName("John Doe");
        createEmployeeDTO.setEmail("john.doe@example.com");
        createEmployeeDTO.setPhone("1234567890");
        createEmployeeDTO.setRole("Developer");
        createEmployeeDTO.setDepartmentId(1L);
        return createEmployeeDTO;
    }

    public EmployeeDTO getMockedEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setPhone("1234567890");
        employeeDTO.setRole("Developer");
        employeeDTO.setDepartmentName("Engineering");
        employeeDTO.setJoinDate(LocalDate.now());
        return employeeDTO;
    }

    public EmployeeDTO getMockedEmployeeDTOWithUpdatedInfo() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setPhone("0987654321");
        employeeDTO.setRole("Senior Developer");
        employeeDTO.setDepartmentName("Engineering");
        employeeDTO.setJoinDate(LocalDate.now());
        return employeeDTO;
    }

    public EmployeeDTO getMockedEmployeeDTOAfterUpdate() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setPhone("0987654321");
        employeeDTO.setRole("Senior Developer");
        employeeDTO.setDepartmentName("Engineering");
        employeeDTO.setJoinDate(LocalDate.now());
        return employeeDTO;
    }

    public UpdateEmployeeDTO getMockedUpdateEmployeeDTO() {
        UpdateEmployeeDTO updateEmployeeDTO = new UpdateEmployeeDTO();
        updateEmployeeDTO.setPhone("0987654321");
        updateEmployeeDTO.setRole("Senior Developer");
        return updateEmployeeDTO;
    }

    public Employee getMockedUpdatedEmployee() {
        Employee employee = getMockedEmployee();
        employee.setPhone("0987654321");
        employee.setRole("Senior Developer");
        return employee;
    }

    public EmployeeDTO getMockedUpdatedEmployeeDTO() {
        EmployeeDTO employeeDTO = getMockedEmployeeDTO();
        employeeDTO.setPhone("0987654321");
        employeeDTO.setRole("Senior Developer");
        return employeeDTO;
    }

    public List<Employee> getMockedEmployees() {
        return List.of(getMockedEmployee());
    }

    public List<EmployeeDTO> getMockedEmployeeDTOs() {
        return List.of(getMockedEmployeeDTO());
    }
}
