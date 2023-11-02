package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.entity.role.Role;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.EmployeeDto;
import com.magicpost.circus.repository.*;
import com.magicpost.circus.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TransactionOfficeRepository transactionOfficeRepository;
    @Autowired
    private StorageOfficeRepository storageOfficeRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public EmployeeServiceImp(EmployeeRepository employeeRepository,
                              RoleRepository roleRepository,
                              TransactionOfficeRepository transactionOfficeRepository,
                              StorageOfficeRepository storageOfficeRepository,
                              TransactionRepository transactionRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.transactionRepository = transactionRepository;
        this.transactionOfficeRepository = transactionOfficeRepository;
        this.storageOfficeRepository = storageOfficeRepository;
    }

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto, Long roleId) {
        Employee employee = this.mapToEntity(employeeDto);
        Role role = this.roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        employee.setRole(roles);
        // save to db
        Employee temp = this.employeeRepository.save(employee);
        return this.mapToDto(temp);
    }

    @Override
    @Transactional
    public EmployeeDto getEmployee(Long id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return this.mapToDto(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        try {
            this.employeeRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Long roleId, Long id,EmployeeDto employeeDto) {
        Optional<Employee> employee = Optional.ofNullable(this.employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id)));
        Employee employeeUpdated = new Employee();
        Role role = this.roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        if (employee.isPresent()) {
            employee.get().setFirstName(employeeDto.getFirstName());
            employee.get().setLastName(employeeDto.getLastName());
            employee.get().setEmail(employeeDto.getEmail());
            employee.get().setPhone(employeeDto.getPhone());
            employee.get().setAddress(employeeDto.getAddress());
            employee.get().setRole(roles);
            employeeUpdated = this.employeeRepository.save(employee.get());
        }

        return this.mapToDto(employeeUpdated);
    }

    @Override
    @Transactional
    public List<EmployeeDto> getEmployees() {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        List<Employee> customer = this.employeeRepository.findAll();
        employeeDtos = customer.stream().map(this::mapToDto).collect(Collectors.toList());
        return employeeDtos;
    }

    private Employee mapToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setAddress(employeeDto.getAddress());

        return employee;
    }

    private EmployeeDto mapToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setPhone(employee.getPhone());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setRole(employee.getRole());
        return employeeDto;
    }
}
