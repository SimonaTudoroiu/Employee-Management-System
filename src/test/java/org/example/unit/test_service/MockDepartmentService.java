package org.example.unit.test_service;

import org.example.dto.CreateDepartmentDTO;
import org.example.dto.DepartmentDTO;
import org.example.dto.EmployeeDTO;
import org.example.model.Department;
import org.example.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockDepartmentService {

    public Department getMockedDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setName("IT Department");
        department.setDescription("Handles all tech-related tasks");
        department.setEmployees(getMockedEmployees());
        return department;
    }

    public CreateDepartmentDTO getMockedCreateDepartmentDTO() {
        CreateDepartmentDTO createDepartmentDTO = new CreateDepartmentDTO();
        createDepartmentDTO.setName("HR Department");
        createDepartmentDTO.setDescription("Handles human resource tasks");
        return createDepartmentDTO;
    }

    public DepartmentDTO getMockedDepartmentDTO() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setName("IT Department");
        departmentDTO.setDescription("Handles all tech-related tasks");
        return departmentDTO;
    }

    public Employee getMockedEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        return employee;
    }

    public EmployeeDTO getMockedEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        return employeeDTO;
    }

    public List<Employee> getMockedEmployees() {
        return List.of(getMockedEmployee());
    }

    public List<EmployeeDTO> getMockedEmployeeDTOs() {
        return List.of(getMockedEmployeeDTO());
    }

    public List<Department> getMockedDepartments() {
        return List.of(getMockedDepartment());
    }

    public List<DepartmentDTO> getMockedDepartmentDTOs() {
        return List.of(getMockedDepartmentDTO());
    }
}
