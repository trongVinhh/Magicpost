package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.CustomerDto;
import com.magicpost.circus.payload.TrackingDto;
import com.magicpost.circus.repository.CustomerRepository;
import com.magicpost.circus.repository.TransactionRepository;
import com.magicpost.circus.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImp implements CustomerService {
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public CustomerServiceImp(CustomerRepository customerRepository,
                              TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = this.mapToEntity(customerDto);

        // save
        Customer newCustomer = this.customerRepository.save(customer);
        return this.mapToDto(newCustomer);
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        return this.mapToDto(customer);
    }

    @Override
    public List<CustomerDto> getCustomers() {
        List<CustomerDto> customerDtos = new ArrayList<>();
        List<Customer> customer = this.customerRepository.findAll();
        customerDtos = customer.stream().map(this::mapToDto).collect(Collectors.toList());
        return customerDtos;
    }

    @Override
    public void deleteCustomer(Long id) {
        try {
            this.customerRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Customer", "id", id);
        }
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Optional<Customer> customer = Optional.ofNullable(this.customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id)));
        Customer customerUpdated = new Customer();

        if (customer.isPresent()) {
            customer.get().setFirstName(customerDto.getFirstName());
            customer.get().setLastName(customerDto.getLastName());
            customer.get().setEmail(customerDto.getEmail());
            customer.get().setPhone(customerDto.getPhone());
            customer.get().setAddress(customerDto.getAddress());
            customerUpdated = this.customerRepository.save(customer.get());
        }

        return this.mapToDto(customerUpdated);
    }

    @Override
    public TrackingDto trackingOrder(String orderCode) {
        Transaction transaction = this.transactionRepository.findByOrderCode(orderCode);
        TrackingDto trackingDto = new TrackingDto();
        trackingDto.setMass(transaction.getMass());
        trackingDto.setOrderCode(transaction.getOrderCode());
        trackingDto.setPhoneNumber(transaction.getPhoneNumber());
        trackingDto.setReceiveAddress(transaction.getReceiveAddress());
        trackingDto.setReceiverName(transaction.getReceiver_name());
        trackingDto.setTotalPrice(transaction.getTotalPrice());
        trackingDto.setDate(transaction.getDate());
        trackingDto.setNameCurrentStorage(transaction.getTransactionId().getName());

        return trackingDto;
    }

    private Customer mapToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setAddress(customerDto.getAddress());
        customer.setPhone(customerDto.getPhone());
        return customer;
    }

    private CustomerDto mapToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setAddress(customer.getAddress());
        customerDto.setPhone(customer.getPhone());
        return customerDto;
    }
}
