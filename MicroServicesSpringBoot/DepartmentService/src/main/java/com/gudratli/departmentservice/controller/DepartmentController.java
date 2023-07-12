package com.gudratli.departmentservice.controller;

import com.gudratli.departmentservice.client.EmployeeClient;
import com.gudratli.departmentservice.entity.Department;
import com.gudratli.departmentservice.entity.Employee;
import com.gudratli.departmentservice.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@Slf4j
public class DepartmentController
{
    private final DepartmentRepository departmentRepository;
    private final EmployeeClient employeeClient;

    @PostMapping
    public Department add (@RequestBody Department department)
    {
        log.info("Adding new department");
        log.info(department.toString());
        return departmentRepository.save(department);
    }

    @GetMapping
    public List<Department> getAll ()
    {
        log.info("Get all department");
        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Department getById (@PathVariable long id)
    {
        log.info("Get by id: " + id);
        return departmentRepository.findById(id).orElse(null);
    }

    @GetMapping("/getAllWithEmployees")
    public List<Department> getAllWithEmployees ()
    {
        log.info("GetAllWithEmployees");
        List<Department> departments = departmentRepository.findAll();
        departments.forEach(department -> {
            List<Employee> employeeList = employeeClient.getByDepartmentId(department.getId());
            List<Long> employeeIds = new ArrayList<>();
            employeeList.forEach(employee -> employeeIds.add(employee.id()));
            department.setEmployeeList(employeeIds);
        });

        return departments;
    }
}
