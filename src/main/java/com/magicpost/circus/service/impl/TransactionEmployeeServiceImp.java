package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.CustomerDto;
import com.magicpost.circus.payload.TransactionDto;
import com.magicpost.circus.repository.*;
import com.magicpost.circus.service.CustomerService;
import com.magicpost.circus.service.TransactionEmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionEmployeeServiceImp implements TransactionEmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TransactionOfficeRepository transactionOfficeRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private StorageOfficeRepository storageOfficeRepository;

    public TransactionEmployeeServiceImp(EmployeeRepository employeeRepository,
                                         TransactionOfficeRepository transactionOfficeRepository,
                                         TransactionRepository transactionRepository,
                                         OrderRepository orderRepository, StorageOfficeRepository storageOfficeRepository) {
        this.employeeRepository = employeeRepository;
        this.transactionOfficeRepository = transactionOfficeRepository;
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
        this.storageOfficeRepository = storageOfficeRepository;
    }

    @Override
    @Transactional
    public TransactionDto createTransaction(TransactionDto transactionDto, Long employeeId, Long transactionOfficeId, Long storageOffcieId) {
        Transaction transaction = this.mapToEntity(transactionDto);
        Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        TransactionOffice transactionOffice = this.transactionOfficeRepository.findById(transactionOfficeId).orElseThrow(() -> new ResourceNotFoundException("TransactionOffice", "id", transactionOfficeId));
        StorageOffice storageOffice = this.storageOfficeRepository.findById(storageOffcieId).orElseThrow(() -> new ResourceNotFoundException("StorageOffice", "id", storageOffcieId));
        Customer customer = transactionDto.getCustomer();

        // set transaction
        transaction.setCustomer(customer);
        transaction.setEmployee(employee);
        transaction.setTransactionId(transactionOffice);

        // order
        Order order = new Order();
        order.setCurrentStorage(storageOffice);
//        transaction.setOrder(order);
        order.setTransactionId(transaction);

        // save to database
        this.orderRepository.save(order);
        Transaction curr = this.transactionRepository.save(transaction);

        return this.mapToDto(curr);
    }

    @Override
    public TransactionDto updateTransaction() {
        return null;
    }

    @Override
    public TransactionDto getTransaction() {
        return null;
    }

    private Transaction mapToEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setMass(transactionDto.getMass());
        transaction.setOrderCode(transactionDto.getOrderCode());
        transaction.setPhoneNumber(transactionDto.getPhoneNumber());
        transaction.setReceiveAddress(transactionDto.getReceiveAddress());
        transaction.setReceiver_name(transactionDto.getReceiverName());
        transaction.setTotalPrice(transactionDto.getTotalPrice());

        return transaction;
    }

    private TransactionDto mapToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setOrderCode(transaction.getOrderCode());
        transactionDto.setReceiverName(transaction.getReceiver_name());
        transactionDto.setTotalPrice(transaction.getTotalPrice());
        transactionDto.setReceiveAddress(transaction.getReceiveAddress());
        transactionDto.setMass(transaction.getMass());
        transactionDto.setPhoneNumber(transaction.getPhoneNumber());



        return transactionDto;
    }
}
