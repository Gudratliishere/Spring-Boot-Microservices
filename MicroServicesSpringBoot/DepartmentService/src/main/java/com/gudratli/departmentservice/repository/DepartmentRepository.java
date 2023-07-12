package com.gudratli.departmentservice.repository;

import com.gudratli.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long>
{
}
