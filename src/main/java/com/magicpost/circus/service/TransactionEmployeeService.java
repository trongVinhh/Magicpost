package com.magicpost.circus.service;

import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.payload.TransactionDto;

public interface TransactionEmployeeService {
    public Transaction createTransaction(TransactionDto transactionDto, Long employeeId, Long transactionOfficeId);
    public Transaction updateTransaction(Long transactionId ,TransactionDto transactionDto, Long employeeId, Long transactionOfficeId, Long storageOfficeId);
    public Transaction getTransaction(Long id);
}
