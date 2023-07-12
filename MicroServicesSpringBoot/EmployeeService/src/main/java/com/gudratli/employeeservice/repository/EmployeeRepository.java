package com.gudratli.employeeservice.repository;

import com.gudratli.employeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
    List<Employee> findByDepartmentId (Long departmentId);
}
