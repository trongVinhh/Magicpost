package com.magicpost.circus.service;

import com.magicpost.circus.payload.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    public EmployeeDto createEmployee(EmployeeDto employeeDto, Long roleId);
    public EmployeeDto getEmployee(Long id);
    public void deleteEmployee(Long id);
    public EmployeeDto updateEmployee(Long roleId, Long id,EmployeeDto employeeDto);
    public List<EmployeeDto> getEmployees();
}
