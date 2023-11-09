package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Account;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.*;
import com.magicpost.circus.repository.EmployeeRepository;
import com.magicpost.circus.repository.OrderRepository;
import com.magicpost.circus.repository.StorageOfficeRepository;
import com.magicpost.circus.repository.TransactionOfficeRepository;
import com.magicpost.circus.service.AccountService;
import com.magicpost.circus.service.DirectorService;
import com.magicpost.circus.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectorServiceImp implements DirectorService {
    @Autowired
    private StorageOfficeRepository storageOfficeRepository;

    @Autowired
    private TransactionOfficeRepository transactionOfficeRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    public DirectorServiceImp(StorageOfficeRepository storageOfficeRepository,
                       TransactionOfficeRepository transactionOfficeRepository,
                              AccountService accountService,
                              EmployeeService employeeService,
                              OrderRepository orderRepository,
                              EmployeeRepository employeeRepository) {
        this.storageOfficeRepository = storageOfficeRepository;
        this.transactionOfficeRepository = transactionOfficeRepository;
        this.accountService = accountService;
        this.employeeService = employeeService;
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
    }
    @Override
    public List<StorageOfficeDto> getAllStorageOffices() {
        List<StorageOffice> storageOffices = this.storageOfficeRepository.findAll();
        List<StorageOfficeDto> storageOfficeDtos = new ArrayList<>();
        storageOffices.forEach(storageOffice -> {
            StorageOfficeDto storageOfficeDto = new StorageOfficeDto();
            storageOfficeDto.setId(storageOffice.getId());
            storageOfficeDto.setName(storageOffice.getName());
            storageOfficeDto.setAddress(storageOffice.getAddress());
//            storageOfficeDto.setOrders(storageOffice.getOrders());
            storageOfficeDto.setEmployees(storageOffice.getEmployees());
//            storageOfficeDto.setManagerId(storageOffice.getManagerId());

            // add to list
            storageOfficeDtos.add(storageOfficeDto);
        });
        return storageOfficeDtos;
    }

    @Override
    public List<TransactionOfficeDto> getAllTransactionOffices() {
        List<TransactionOffice> transactionOffices = this.transactionOfficeRepository.findAll();
        List<TransactionOfficeDto> transactionOfficeDtos = new ArrayList<>();
        transactionOffices.forEach(transactionOffice -> {
            TransactionOfficeDto transactionOfficeDto = new TransactionOfficeDto();
            transactionOfficeDto.setId(transactionOffice.getId());
            transactionOfficeDto.setName(transactionOffice.getName());
            transactionOfficeDto.setAddress(transactionOffice.getAddress());
            transactionOfficeDto.setHotline(transactionOffice.getHotline());
            transactionOfficeDto.setEmail(transactionOffice.getEmail());

            // add to list
            transactionOfficeDtos.add(transactionOfficeDto);
        });

        return transactionOfficeDtos;
    }


    @Override
    public List<EmployeeDto> getAllEmployeesOfStorageOffice(Long storageOfficeId) {
        return null;
    }

    @Override
    public List<EmployeeDto> getAllEmployeesOfTransactionOffice(Long transactionOfficeId) {
        return null;
    }

    @Override
    public List<Order> getAllOrdersInStorage(Long storageOfficeId) {
        StorageOffice storageOffice = this.storageOfficeRepository.findById(storageOfficeId)
                .orElseThrow(() -> new ResourceNotFoundException("Storage Office", "id", storageOfficeId));
        List<Order> orders = storageOffice.getOrders();
        return orders;
    }

    private Account mapToAccountEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setUserName(accountDto.getUsername());
        account.setPassword(accountDto.getPassword());
        return account;
    }

}
