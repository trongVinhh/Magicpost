package com.magicpost.circus.entity.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.entity.role.Role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "employee_role",
                joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    public Set<Role> role;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private StorageOffice storageOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_office_id", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    @JsonBackReference
    private TransactionOffice transactionOffice;

}
