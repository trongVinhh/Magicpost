package com.magicpost.circus.entity.person.child;

import com.magicpost.circus.entity.person.Employee;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_transaction")
@DiscriminatorValue("EMPLOYEE_TRANSACTION")
public class EmployeeTransaction extends Employee {
}
