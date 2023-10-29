package com.magicpost.circus.entity.person.child;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Account;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.entity.role.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "manager")
public class Manager extends Employee {
    @OneToOne(mappedBy = "managerId")
    private StorageOffice storageOffice;
    @OneToOne(mappedBy = "managerId")
    private TransactionOffice transactionOffice;
}
