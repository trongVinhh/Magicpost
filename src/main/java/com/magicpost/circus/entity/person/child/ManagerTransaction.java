package com.magicpost.circus.entity.person.child;

import com.magicpost.circus.entity.person.Employee;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MANAGER_TRANSACTION")
public class ManagerTransaction extends Employee {
}
