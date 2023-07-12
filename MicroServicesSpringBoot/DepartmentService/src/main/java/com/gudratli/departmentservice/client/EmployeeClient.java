package com.gudratli.departmentservice.client;

import com.gudratli.departmentservice.entity.Employee;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface EmployeeClient
{
    @GetExchange("/employee/department/{id}")
    List<Employee> getByDepartmentId (@PathVariable("id") Long id);
}
