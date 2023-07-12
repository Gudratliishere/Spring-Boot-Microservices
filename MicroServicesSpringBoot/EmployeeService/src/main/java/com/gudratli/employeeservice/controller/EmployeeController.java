package com.gudratli.employeeservice.controller;

import com.gudratli.employeeservice.entity.Employee;
import com.gudratli.employeeservice.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController
{
    private final EmployeeRepository employeeRepository;

    @PostMapping
    public Employee add (@RequestBody Employee employee)
    {
        log.info("Adding new employee");
        log.info(employee.toString());

        return employeeRepository.save(employee);
    }

    @GetMapping
    public List<Employee> getAll ()
    {
        log.info("Get all employees");
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Employee getById (@PathVariable Long id)
    {
        log.info("Get employee by id: " + id);
        return employeeRepository.findById(id).orElse(null);
    }

    @GetMapping("/department/{id}")
    public List<Employee> getByDepartmentId (@PathVariable Long id)
    {
        log.info("Get employees by department id: " + id);
        return employeeRepository.findByDepartmentId(id);
    }
}
