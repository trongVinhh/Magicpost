package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.entity.info.PackageTransfer;
import com.magicpost.circus.entity.info.Tracking;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.CustomerDto;
import com.magicpost.circus.payload.OrderDto;
import com.magicpost.circus.payload.TransactionDto;
import com.magicpost.circus.repository.*;
import com.magicpost.circus.service.CustomerService;
import com.magicpost.circus.service.TransactionEmployeeService;
import com.magicpost.circus.ultis.PackageTransferStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private TrackingRepository trackingRepository;
    @Autowired
    private PackageTransferRepository packageTransferRepository;
    private PackageTransferStatus PackageTransferStatus;

    public TransactionEmployeeServiceImp(EmployeeRepository employeeRepository,
                                         TransactionOfficeRepository transactionOfficeRepository,
                                         TransactionRepository transactionRepository,
                                         OrderRepository orderRepository, StorageOfficeRepository storageOfficeRepository,
                                         TrackingRepository trackingRepository,
                                         PackageTransferRepository packageTransferRepository) {
        this.employeeRepository = employeeRepository;
        this.transactionOfficeRepository = transactionOfficeRepository;
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
        this.storageOfficeRepository = storageOfficeRepository;
        this.trackingRepository = trackingRepository;
        this.packageTransferRepository = packageTransferRepository;
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
        this.trackingRepository.save(tracking);
        Transaction temp = this.transactionRepository.save(transaction);
        return this.mapToDto(temp);
    }

    @Override
    @Transactional
    public TransactionDto updateTransaction(Long transactionId ,TransactionDto transactionDto) {
        Transaction transaction = this.transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));
        Long employeeId = transactionDto.getEmployeeId();
        Long transactionOfficeId = transactionDto.getTransactionOfficeId();
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
        Customer customer = this.mapToCustomerEntity(transactionDto.getCustomerDto());
        transaction.setCustomer(customer);
        transaction.setDate(transactionDto.getDate());
        transaction.setTransactionId(transactionOffice);

        // save to database
        Transaction temp = this.transactionRepository.save(transaction);
        return this.mapToDto(temp);
    }

    @Override
    @Transactional
    public TransactionDto getTransaction(Long id) {
        Transaction transaction = this.transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", id));

        return this.mapToDto(transaction);
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = this.transactionRepository.findAll();
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactions.forEach(transaction1 -> {
            TransactionDto transactionDto = this.mapToDto(transaction1);

            // add to list
            transactionDtos.add(transactionDto);
        });
        return transactionDtos;
    }

    @Override
    public void transferPackageToStorage(String orderCode, Long storageId, Long transactionOfficeId) {
        Transaction transaction = this.transactionRepository.findByOrderCode(orderCode);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction", orderCode);
        }
        TransactionOffice transactionOffice = this.transactionOfficeRepository.findById(transactionOfficeId).orElseThrow(() -> new ResourceNotFoundException("TransactionOffice", "id", transactionOfficeId));
        StorageOffice storageOffice = this.storageOfficeRepository.findById(storageId).orElseThrow(() -> new ResourceNotFoundException("StorageOffice", "id", storageId));

        PackageTransfer packageTransfer = new PackageTransfer();
        packageTransfer.setOrderCode(orderCode);
        packageTransfer.setFrom(PackageTransferStatus.TransactionOfficeToStorage);
        packageTransfer.setStorageId(storageId);
        packageTransfer.setTransactionOfficeId(transactionOfficeId);
        this.packageTransferRepository.save(packageTransfer);
    }

    @Override
    public void confirmPackageReceived() {
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
