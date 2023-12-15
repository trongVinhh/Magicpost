package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.entity.role.Role;
import com.magicpost.circus.exception.MagicPostException;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.EmployeeDto;
import com.magicpost.circus.repository.*;
import com.magicpost.circus.service.EmployeeService;
import com.magicpost.circus.ultis.EmployeeRole;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    public EmployeeServiceImp(EmployeeRepository employeeRepository,
                              RoleRepository roleRepository,
                              TransactionOfficeRepository transactionOfficeRepository,
                              StorageOfficeRepository storageOfficeRepository,
                              TransactionRepository transactionRepository,
                              PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.transactionRepository = transactionRepository;
        this.transactionOfficeRepository = transactionOfficeRepository;
        this.storageOfficeRepository = storageOfficeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto, Long roleId) {
        // check exist employee
        if (employeeRepository.existsByUsername(employeeDto.getUsername())) {
            throw new MagicPostException(HttpStatus.BAD_REQUEST,"Username is already taken!");
        }
        // check exist email
        if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new MagicPostException(HttpStatus.BAD_REQUEST,"Email is already taken!");
        }

        Employee employee = this.mapToEntity(employeeDto);
        Role role = this.roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
        //Check role
        String roleName = role.getName();

        // add role to employee
        Set<Role> roles = new HashSet<>();
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
    public EmployeeDto getEmployeeByUsername(String username) {
        Employee employee = this.employeeRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Employee", "username"));
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

        Set<Role> roles = new HashSet<>();
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
        employee.setUsername(employeeDto.getUsername());
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        return employee;
    }

    private EmployeeDto mapToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setPhone(employee.getPhone());
        employeeDto.setUsername(employee.getUsername());
        employeeDto.setPassword(employee.getPassword());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setRole(employee.getRole());
        return employeeDto;
    }


}
