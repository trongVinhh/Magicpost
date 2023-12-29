package com.magicpost.circus.service;

import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.payload.EmployeeDto;
import com.magicpost.circus.payload.PackageTransferResponse;

import java.util.List;

public interface EmployeeService {
    public EmployeeDto createEmployee(EmployeeDto employeeDto, Long roleId);
    public EmployeeDto getEmployee(Long id);
    public void deleteEmployee(Long id);
    public EmployeeDto updateEmployee(Long roleId, Long id,EmployeeDto employeeDto);
    public List<EmployeeDto> getEmployees();
    public EmployeeDto getEmployeeByUsername(String username);

    public List<EmployeeDto> searchEmployee(String keyword);

    public List<PackageTransferResponse> getAllPackageTransferring();
}
