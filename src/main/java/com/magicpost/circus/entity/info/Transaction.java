package com.magicpost.circus.entity.info;

import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.entity.person.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long id;
    private Long totalPrice;
    private Long mass;
    private Employee employee;
    private Customer customer;
}
