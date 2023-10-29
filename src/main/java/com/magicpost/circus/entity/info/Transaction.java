package com.magicpost.circus.entity.info;

import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.entity.person.Employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "total_price")
    private Long totalPrice;
    @Column(name = "mass")
    private Long mass;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne(mappedBy = "transactionId", cascade = CascadeType.ALL)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_office_id", referencedColumnName = "id")
    private TransactionOffice transactionId;
}
