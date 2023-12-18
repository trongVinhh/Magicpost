package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.EmployeeDto;
import com.magicpost.circus.payload.InfoUserResponse;
import com.magicpost.circus.payload.StorageOfficeDto;
import com.magicpost.circus.payload.TransactionOfficeDto;
import com.magicpost.circus.repository.EmployeeRepository;
import com.magicpost.circus.repository.StorageOfficeRepository;
import com.magicpost.circus.repository.TransactionOfficeRepository;
import com.magicpost.circus.service.ManagerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImp implements ManagerService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private StorageOfficeRepository storageOfficeRepository;

    @Autowired
    private TransactionOfficeRepository transactionOfficeRepository;


    public ManagerServiceImp(EmployeeRepository employeeRepository,
                             StorageOfficeRepository storageOfficeRepository,
                             TransactionOfficeRepository transactionOfficeRepository) {
        this.employeeRepository = employeeRepository;
        this.storageOfficeRepository = storageOfficeRepository;
        this.transactionOfficeRepository = transactionOfficeRepository;
    }

    @Override
    @Transactional
    public StorageOfficeDto setStorageOfficeForEmployee(Long employeeId, Long storageOfficeId) {
        Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        StorageOffice storageOffice = this.storageOfficeRepository.findById(storageOfficeId).orElseThrow(() -> new ResourceNotFoundException("StorageOffice", "id", storageOfficeId));

        employee.setStorageOffice(storageOffice);
        // save to db
        this.employeeRepository.save(employee);

        return this.mapToStorageOfficeDto(storageOffice);
    }

    @Override
    @Transactional
    public TransactionOfficeDto setTransactionOfficeForEmployee(Long employeeId, Long transactionOfficeId) {

        Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        TransactionOffice transactionOffice = this.transactionOfficeRepository.findById(transactionOfficeId).orElseThrow(() -> new ResourceNotFoundException("TransactionOffice", "id", transactionOfficeId));

        employee.setTransactionOffice(transactionOffice);
        // save to db
        this.employeeRepository.save(employee);
        return this.mapToTransactionOfficeDto(transactionOffice);
    }

    @Override
    public InfoUserResponse getStorageIdFromUsername(String username) {
        Employee employee = this.employeeRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Employee", username));

        Long storageOfficeId = employee.getStorageOffice().getId();
        InfoUserResponse info = new InfoUserResponse();
        info.setUsername(username);
        info.setOfficeId(storageOfficeId);
        return info;
    }

    @Override
    public InfoUserResponse getTransactionOfficeIdFromUsername(String username) {
        Employee employee = this.employeeRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Employee", username));

        Long id = employee.getTransactionOffice().getId();
        InfoUserResponse info = new InfoUserResponse();
        info.setUsername(username);
        info.setOfficeId(id);
        return info;
    }

    @Override
    public StorageOfficeDto getStorageById(Long id) {
        StorageOffice storageOffice = this.storageOfficeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("StorageOffice", "id", id));
        return this.mapToStorageOfficeDto(storageOffice);
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

    private StorageOfficeDto mapToStorageOfficeDto(StorageOffice storageOffice) {
        StorageOfficeDto storageOfficeDto = new StorageOfficeDto();
        storageOfficeDto.setId(storageOffice.getId());
        storageOfficeDto.setName(storageOffice.getName());
        storageOfficeDto.setAddress(storageOffice.getAddress());
        storageOfficeDto.setEmployees(storageOffice.getEmployees());

        return storageOfficeDto;
    }

    private TransactionOfficeDto mapToTransactionOfficeDto(TransactionOffice transactionOffice) {
        TransactionOfficeDto transactionOfficeDto = new TransactionOfficeDto();
        transactionOfficeDto.setId(transactionOffice.getId());
        transactionOfficeDto.setName(transactionOffice.getName());
        transactionOfficeDto.setAddress(transactionOffice.getAddress());
        transactionOfficeDto.setEmail(transactionOffice.getEmail());
        transactionOfficeDto.setHotline(transactionOffice.getHotline());
        transactionOfficeDto.setEmployees(transactionOffice.getEmployees());

        return transactionOfficeDto;
    }
}
