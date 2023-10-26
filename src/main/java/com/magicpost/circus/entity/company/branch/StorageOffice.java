package com.magicpost.circus.entity.company.branch;

import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.entity.person.child.Manager;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageOffice {
    private String id;
    private String name;
    private String address;

    private Manager manager_id;
    private List<Employee> employees;
    private List<Order> orders;
}
