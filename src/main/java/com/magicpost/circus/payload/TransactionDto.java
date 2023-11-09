package com.magicpost.circus.payload;

import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.entity.person.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDto {
    private Long id;
    private Long totalPrice;
    private Long mass;
    private String orderCode;
    private String receiveAddress;
    private String receiverName;
    private String phoneNumber;
    private Date date;
    private Long employeeId;
    private Long transactionOfficeId;
    private Employee employee;
    private Customer customer;
    private Order order;
    private TransactionOffice transactionId;

}
