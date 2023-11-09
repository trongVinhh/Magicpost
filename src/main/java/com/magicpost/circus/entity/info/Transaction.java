package com.magicpost.circus.entity.info;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.entity.person.Employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    @Column(name = "order_code")
    private String orderCode;
    @Column(name = "receive_address")
    private String receiveAddress;
    @Column(name = "receiver_name")
    private String receiver_name;
    @Column(name = "phone_number")
    private String phoneNumber;
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "date")
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @JsonIgnore
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonBackReference
    private Customer customer;

    @OneToOne(mappedBy = "transactionId", cascade = CascadeType.ALL)
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_office_id", referencedColumnName = "id")
    @JsonIgnore
    private TransactionOffice transactionId;
}
