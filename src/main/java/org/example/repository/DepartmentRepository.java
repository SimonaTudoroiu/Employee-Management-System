package org.example.repository;

import org.example.model.Department;
import org.example.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findById(Long id);
    List<Employee> findEmployeesByDepartmentId(Long departmentId);
    void deleteById(Long id);
}
