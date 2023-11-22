package com.magicpost.circus.service;

import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.payload.CustomerDto;
import com.magicpost.circus.payload.TrackingDto;

import java.util.List;

public interface CustomerService {
    public CustomerDto createCustomer(CustomerDto customerDto);
    public CustomerDto getCustomer(Long id);
    public List<CustomerDto> getCustomers();
    public void deleteCustomer(Long id);
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto);

    public TrackingDto trackingOrder(String orderCode);

}
