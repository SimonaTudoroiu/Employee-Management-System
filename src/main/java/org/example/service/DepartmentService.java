package org.example.service;

import org.example.model.Department;
import org.example.model.Employee;
import org.example.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        return departmentRepository.findEmployeesByDepartmentId(departmentId);
    }
}
