package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.entity.info.Tracking;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.exception.NotAuthorizeException;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.CustomerDto;
import com.magicpost.circus.payload.TransactionDto;
import com.magicpost.circus.repository.*;
import com.magicpost.circus.service.CustomerService;
import com.magicpost.circus.service.TransactionEmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    public TransactionEmployeeServiceImp(EmployeeRepository employeeRepository,
                                         TransactionOfficeRepository transactionOfficeRepository,
                                         TransactionRepository transactionRepository,
                                         OrderRepository orderRepository, StorageOfficeRepository storageOfficeRepository,
                                         OrderHistoryRepository orderHistoryRepository) {
        this.employeeRepository = employeeRepository;
        this.transactionOfficeRepository = transactionOfficeRepository;
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
        this.storageOfficeRepository = storageOfficeRepository;
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Override
    @Transactional
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = this.mapToEntity(transactionDto);

        Long employeeId = transactionDto.getEmployeeId();
        Long transactionOfficeId = transactionDto.getTransactionOfficeId();
        Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        TransactionOffice transactionOffice = this.transactionOfficeRepository.findById(transactionOfficeId).orElseThrow(() -> new ResourceNotFoundException("TransactionOffice", "id", transactionOfficeId));
        CustomerDto customerDto = transactionDto.getCustomerDto();
        Customer customer = this.mapToCustomerEntity(customerDto);
        // set transaction
        transaction.setCustomer(customer);
        transaction.setEmployee(employee);
        transaction.setTransactionId(transactionOffice);

        // order
        Order order = new Order();
        order.setTransactionId(transaction);

        // tracking
        Tracking tracking = new Tracking();
        tracking.setStatus("Đang giao hàng");
        tracking.setOrderId(order);


        // save to database
        this.orderRepository.save(order);
        this.orderHistoryRepository.save(tracking);
        Transaction temp = this.transactionRepository.save(transaction);
        return this.mapToDto(temp);
    }

    @Override
    @Transactional
    public Transaction updateTransaction(Long transactionId ,TransactionDto transactionDto, Long employeeId, Long transactionOfficeId, Long storageOfficeId) {
        Transaction transaction = this.transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));
        Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        TransactionOffice transactionOffice = this.transactionOfficeRepository.findById(transactionOfficeId).orElseThrow(() -> new ResourceNotFoundException("TransactionOffice", "id", transactionOfficeId));


        // set transaction
        transaction.setEmployee(employee);
        transaction.setTransactionId(transactionOffice);
        transaction.setMass(transactionDto.getMass());
        transaction.setOrderCode(transactionDto.getOrderCode());
        transaction.setPhoneNumber(transactionDto.getPhoneNumber());
        transaction.setReceiveAddress(transactionDto.getReceiveAddress());
        transaction.setReceiver_name(transactionDto.getReceiverName());
        transaction.setTotalPrice(transactionDto.getTotalPrice());
//        transaction.setCustomer(transactionDto.getCustomer());

        // save to database
        this.transactionRepository.save(transaction);
        return null;
    }

    @Override
    @Transactional
    public Transaction getTransaction(Long id) {
        Transaction transaction = this.transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", id));
        return transaction;
    }

    private Transaction mapToEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setMass(transactionDto.getMass());
        transaction.setOrderCode(transactionDto.getOrderCode());
        transaction.setPhoneNumber(transactionDto.getPhoneNumber());
        transaction.setReceiveAddress(transactionDto.getReceiveAddress());
        transaction.setReceiver_name(transactionDto.getReceiverName());
        transaction.setTotalPrice(transactionDto.getTotalPrice());
        transaction.setDate(transactionDto.getDate());

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
        transactionDto.setEmployeeId(transaction.getEmployee().getId());
        transactionDto.setTransactionOfficeId(transaction.getTransactionId().getId());
        transactionDto.setCustomerDto(this.mapToCustomerDto(transaction.getCustomer()));
        transactionDto.setDate(transaction.getDate());

        return transactionDto;
    }

    private Customer mapToCustomerEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhone(customerDto.getPhone());
        customer.setAddress(customerDto.getAddress());
        customer.setEmail(customerDto.getEmail());

        return customer;
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
}
