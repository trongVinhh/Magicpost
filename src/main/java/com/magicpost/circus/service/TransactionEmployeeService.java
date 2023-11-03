package com.magicpost.circus.service;

import com.magicpost.circus.payload.TransactionDto;

public interface TransactionEmployeeService {
    public TransactionDto createTransaction(TransactionDto transactionDto, Long employeeId, Long transactionOfficeId, Long storageOffcieId);
    public TransactionDto updateTransaction();
    public TransactionDto getTransaction();
}
