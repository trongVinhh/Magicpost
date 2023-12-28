package com.magicpost.circus.controller.customer;

import com.magicpost.circus.payload.CustomerDto;
import com.magicpost.circus.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE_TRANSACTION')")
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody  CustomerDto customerDto) {
        CustomerDto dto = this.customerService.createCustomer(customerDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {
        CustomerDto dto = this.customerService.getCustomer(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerDto> dtos = this.customerService.getCustomers();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        this.customerService.deleteCustomer(id);
        return new ResponseEntity<>("Customer id: " + id + " was deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        CustomerDto dto = this.customerService.updateCustomer(id, customerDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_TRANSACTION', " +
            "'ROLE_MANAGER_STORAGE', 'ROLE_EMPLOYEE_TRANSACTION', 'ROLE_EMPLOYEE_STORAGE')")
    @GetMapping("/phone")
    public ResponseEntity<List<CustomerDto>> getCustomerByPhoneNumber(@RequestParam("phone") String phone) {
        List<CustomerDto> dtos = this.customerService.getCustomerByPhone(phone);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_TRANSACTION', " +
            "'ROLE_MANAGER_STORAGE', 'ROLE_EMPLOYEE_TRANSACTION', 'ROLE_EMPLOYEE_STORAGE')")
    @GetMapping("/name")
    public ResponseEntity<List<CustomerDto>> getCustomerByName(@RequestParam("name") String name) {
        List<CustomerDto> dtos = this.customerService.getCustomerByName(name);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
