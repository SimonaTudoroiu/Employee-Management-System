package org.example.controller;

import org.example.dto.CreateDepartmentDTO;
import org.example.dto.DepartmentDTO;
import org.example.dto.EmployeeDTO;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentDTO createDepartment(@RequestBody CreateDepartmentDTO department) {
        return departmentService.createDepartment(department);
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeDTO> getEmployeesByDepartmentId(@PathVariable Long id) {
        return departmentService.getEmployeesByDepartmentId(id);
    }

    @GetMapping
    public List<DepartmentDTO> getDepartments() {
        return departmentService.getDepartments();
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

}
