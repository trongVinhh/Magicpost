package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.entity.info.Tracking;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.*;
import com.magicpost.circus.repository.*;
import com.magicpost.circus.service.DirectorService;
import com.magicpost.circus.service.EmployeeService;
import com.magicpost.circus.service.TransactionEmployeeService;
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
    private OrderRepository orderRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionEmployeeService transactionEmployeeService;

    public DirectorServiceImp(StorageOfficeRepository storageOfficeRepository,
                       TransactionOfficeRepository transactionOfficeRepository,

                              EmployeeService employeeService,
                              OrderRepository orderRepository,
                              EmployeeRepository employeeRepository,
                              TransactionRepository transactionRepository,
                              TransactionEmployeeService transactionEmployeeService
                             ) {
        this.storageOfficeRepository = storageOfficeRepository;
        this.transactionOfficeRepository = transactionOfficeRepository;
        this.employeeService = employeeService;
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.transactionRepository = transactionRepository;
        this.transactionEmployeeService = transactionEmployeeService;
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
            transactionOfficeDto.setEmployees(transactionOffice.getEmployees());

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
    public List<OrderDto> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        orders.forEach(order -> {
            OrderDto orderDto = new OrderDto();
            TransactionDto transactionDto = this.mapToTransactionDto(order.getTransactionId());
            StorageOfficeDto storageOfficeDto = this.mapToStorageOfficeDto(order.getCurrentStorage());
            Tracking orderTracking = order.getTracking();
            orderDto.setId(order.getId());
            orderDto.setCurrentStorageName(storageOfficeDto.getName());
            orderDto.setCurrentStorageAddress(storageOfficeDto.getAddress());
            orderDto.setTransaction(transactionDto);
            orderDto.setStatus(orderTracking.getStatus());
            orderDtos.add(orderDto);
        });
        return orderDtos;
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = this.transactionRepository.findAll();
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactions.forEach(transaction -> {
            TransactionDto transactionDto = this.mapToTransactionDto(transaction);

            // add to list
            transactionDtos.add(transactionDto);
        });
        return transactionDtos;
    }

    @Override
    public List<OrderDto> getAllOrdersInStorage(Long storageOfficeId) {
        StorageOffice storageOffice = this.storageOfficeRepository.findById(storageOfficeId)
                .orElseThrow(() -> new ResourceNotFoundException("Storage Office", "id", storageOfficeId));
        List<Order> orders = storageOffice.getOrders();
        List<OrderDto> orderDtos = new ArrayList<>();
        orders.forEach(order -> {
            OrderDto orderDto = new OrderDto();
            TransactionDto transactionDto = this.mapToTransactionDto(order.getTransactionId());
            StorageOfficeDto storageOfficeDto = this.mapToStorageOfficeDto(order.getCurrentStorage());
            Tracking orderTracking = order.getTracking();
            orderDto.setId(order.getId());
            orderDto.setCurrentStorageName(storageOfficeDto.getName());
            orderDto.setCurrentStorageAddress(storageOfficeDto.getAddress());
            orderDto.setTransaction(transactionDto);
            orderDto.setStatus(orderTracking.getStatus());
            orderDtos.add(orderDto);
        });
        return orderDtos;
    }

    @Override
    public List<TransactionDto> getAllTransactionsInTransactionOffice(Long transactionOfficeId) {
        TransactionOffice transactionOffice = this.transactionOfficeRepository.findById(transactionOfficeId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction Office", "id", transactionOfficeId));
        List<Transaction> transactions = transactionOffice.getTransactions();
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactions.forEach(transaction -> {
            TransactionDto transactionDto = this.mapToTransactionDto(transaction);
            Order order = transaction.getOrder();
            Tracking tracking = order.getTracking();
            transactionDto.setStatus(tracking.getStatus());
            // add to list
            transactionDtos.add(transactionDto);
        });
        return transactionDtos;
    }


    private CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPhone(customer.getPhone());
        customerDto.setAddress(customer.getAddress());
        customerDto.setEmail(customer.getEmail());

        return customerDto;
    }

    private TransactionDto mapToTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setOrderCode(transaction.getOrderCode());
        transactionDto.setReceiverName(transaction.getReceiver_name());
        transactionDto.setTotalPrice(transaction.getTotalPrice());
        transactionDto.setReceiveAddress(transaction.getReceiveAddress());
        transactionDto.setPhoneNumber(transaction.getPhoneNumber());
        transactionDto.setCustomerDto(this.mapToCustomerDto(transaction.getCustomer()));
        transactionDto.setMass(transaction.getMass());
        transactionDto.setEmployeeId(transaction.getEmployee().getId());
        transactionDto.setTransactionOfficeId(transaction.getTransactionId().getId());
        transactionDto.setDate(transaction.getDate());
        transactionDto.setPostage(transaction.getPostage());
        transactionDto.setPackageType(transaction.getPackageType());
        return transactionDto;
    }

//    private OrderDto mapToOrderDto(Order order) {
//        OrderDto orderDto = new OrderDto();
//        orderDto.setId(order.getId());
//        if (order.getCurrentStorage() == null) {
//            orderDto.setCurrentStorageId(null);
//        } else {
//            orderDto.setCurrentStorageId(order.getCurrentStorage().getId());
//        }
//        orderDto.setTransactionId(order.getTransactionId().getId());
//
//        return orderDto;
//    }

    private StorageOfficeDto mapToStorageOfficeDto(StorageOffice storageOffice) {
        StorageOfficeDto storageOfficeDto = new StorageOfficeDto();
        storageOfficeDto.setId(storageOffice.getId());
        storageOfficeDto.setName(storageOffice.getName());
        storageOfficeDto.setAddress(storageOffice.getAddress());
        storageOfficeDto.setEmployees(storageOffice.getEmployees());

        return storageOfficeDto;
    }


}
