package com.magicpost.circus.entity.company.branch;

import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.entity.person.Manager;

import java.util.List;

public class TransactionOffice {
    private String id;
    private String name;
    private String address;

    private Manager manager_id;
    private List<Employee> employees;
}
