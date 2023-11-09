package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.entity.person.child.*;
import com.magicpost.circus.entity.role.Role;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.EmployeeDto;
import com.magicpost.circus.repository.*;
import com.magicpost.circus.repository.employee.*;
import com.magicpost.circus.service.EmployeeService;
import com.magicpost.circus.ultis.EmployeeRole;
import jakarta.persistence.EntityManager;
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
    @Autowired
    private ShipperRepository shipperRepository;
    @Autowired
    private ManagerStorageRepository managerStorageRepository;
    @Autowired
    private ManagerTransactionRepository managerTransactionRepository;
    @Autowired
    private EmployeeTransactionRepository employeeTransactionRepository;
    @Autowired
    private EmployeeStorageRepository employeeStorageRepository;

    @Autowired
    private EntityManager entityManager;

    public EmployeeServiceImp(EmployeeRepository employeeRepository,
                              RoleRepository roleRepository,
                              TransactionOfficeRepository transactionOfficeRepository,
                              StorageOfficeRepository storageOfficeRepository,
                              TransactionRepository transactionRepository,
                              ShipperRepository shipperRepository,
                              ManagerStorageRepository managerStorageRepository,
                              ManagerTransactionRepository managerTransactionRepository,
                              EmployeeTransactionRepository employeeTransactionRepository,
                              EmployeeStorageRepository employeeStorageRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.transactionRepository = transactionRepository;
        this.transactionOfficeRepository = transactionOfficeRepository;
        this.storageOfficeRepository = storageOfficeRepository;
        this.shipperRepository = shipperRepository;
        this.managerStorageRepository = managerStorageRepository;
        this.managerTransactionRepository = managerTransactionRepository;
        this.employeeTransactionRepository = employeeTransactionRepository;
        this.employeeStorageRepository = employeeStorageRepository;
    }

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto, Long roleId) {
        Employee employee = this.mapToEntity(employeeDto);
        Role role = this.roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
        //Check role
        String roleName = role.getName();
        this.checkRoleAndSave(roleName, employee);

        // add role to employee
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

    private void checkRoleAndSave(String roleName, Employee employee) {

        switch (EmployeeRole.valueOf(roleName)) {
            case SHIPPER:
                Shipper shipper = this.mapToShipperEntity(employee);

                // save to db
                this.shipperRepository.save(shipper);
                break;
            case EMPLOYEE_TRANSACTION:
                EmployeeTransaction employeeTransaction = this.mapToEmployeeTransactionEntity(employee);

                // save to db
                this.employeeTransactionRepository.save(employeeTransaction);
                break;
            case EMPLOYEE_STORAGE:
                EmployeeStorage employeeStorage = this.mapToEmployeeStorageEntity(employee);

                //save to db
                this.employeeStorageRepository.save(employeeStorage);
                break;
            case MANAGER_STORAGE:
                ManagerStorage managerStorage = this.mapToManagerStorageEntity(employee);

                //save to db
                this.managerStorageRepository.save(managerStorage);
                break;
            case MANAGER_TRANSACTION:
                ManagerTransaction managerTransaction = this.mapToManagerTransactionEntity(employee);

                //save to db
                this.managerTransactionRepository.save(managerTransaction);
                break;
        }
    }

    private Shipper mapToShipperEntity(Employee employee) {
        Shipper shipper = new Shipper();
        shipper.setFirstName(employee.getFirstName());
        shipper.setLastName(employee.getLastName());
        shipper.setEmail(employee.getEmail());
        shipper.setPhone(employee.getPhone());
        shipper.setAddress(employee.getAddress());

        return shipper;
    }

    private ManagerStorage mapToManagerStorageEntity(Employee employee) {
        ManagerStorage managerStorage = new ManagerStorage();
        managerStorage.setFirstName(employee.getFirstName());
        managerStorage.setLastName(employee.getLastName());
        managerStorage.setEmail(employee.getEmail());
        managerStorage.setPhone(employee.getPhone());
        managerStorage.setAddress(employee.getAddress());

        return managerStorage;
    }

    private ManagerTransaction mapToManagerTransactionEntity(Employee employee) {
        ManagerTransaction managerTransaction = new ManagerTransaction();
        managerTransaction.setFirstName(employee.getFirstName());
        managerTransaction.setLastName(employee.getLastName());
        managerTransaction.setEmail(employee.getEmail());
        managerTransaction.setPhone(employee.getPhone());
        managerTransaction.setAddress(employee.getAddress());

        return managerTransaction;
    }

    private EmployeeStorage mapToEmployeeStorageEntity(Employee employee) {
        EmployeeStorage employeeStorage = new EmployeeStorage();
        employeeStorage.setFirstName(employee.getFirstName());
        employeeStorage.setLastName(employee.getLastName());
        employeeStorage.setEmail(employee.getEmail());
        employeeStorage.setPhone(employee.getPhone());
        employeeStorage.setAddress(employee.getAddress());

        return employeeStorage;
    }

    private EmployeeTransaction mapToEmployeeTransactionEntity(Employee employee) {
        EmployeeTransaction employeeTransaction = new EmployeeTransaction();
        employeeTransaction.setFirstName(employee.getFirstName());
        employeeTransaction.setLastName(employee.getLastName());
        employeeTransaction.setEmail(employee.getEmail());
        employeeTransaction.setPhone(employee.getPhone());
        employeeTransaction.setAddress(employee.getAddress());

        return employeeTransaction;
    }
}
