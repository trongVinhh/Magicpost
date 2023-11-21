package com.magicpost.circus.service;

import com.magicpost.circus.payload.*;

import java.util.List;

public interface DirectorService {
    List<StorageOfficeDto> getAllStorageOffices();
    List<TransactionOfficeDto> getAllTransactionOffices();

    List<EmployeeDto> getAllEmployeesOfStorageOffice(Long storageOfficeId);
    List<EmployeeDto> getAllEmployeesOfTransactionOffice(Long transactionOfficeId);

    List<OrderDto> getAllOrders();
    List<TransactionDto> getAllTransactions();

    List<OrderDto> getAllOrdersInStorage(Long storageOfficeId);
    List<TransactionDto> getAllTransactionsInTransactionOffice(Long transactionOfficeId);

}
