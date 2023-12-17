package com.magicpost.circus.service;

import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.payload.OrderDto;
import com.magicpost.circus.payload.TransactionDto;

import java.util.List;

public interface TransactionEmployeeService {
    public TransactionDto createTransaction(TransactionDto transactionDto) throws Exception;
    public TransactionDto updateTransaction(Long transactionId ,TransactionDto transactionDto);
    public TransactionDto getTransaction(Long id);
    public List<TransactionDto> getAllTransactions();

    public void transferPackageToStorage(String orderCode, Long storageId, Long transactionOfficeId);
    public void confirmPackageReceived();
}
