package com.magicpost.circus.service;

import com.magicpost.circus.payload.TransactionDto;

public interface TransactionEmployeeService {
    public TransactionDto createTransaction();
    public TransactionDto updateTransaction();
    public TransactionDto getTransaction();
}
