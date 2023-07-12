package com.gudratli.departmentservice.entity;

public record Employee(Long id,
                       Long departmentId,
                       String name,
                       Integer age,
                       String position)
{
}
