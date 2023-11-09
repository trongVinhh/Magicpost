package com.magicpost.circus.service;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Account;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.payload.*;

import java.util.List;

public interface DirectorService {
    List<StorageOfficeDto> getAllStorageOffices();
    List<TransactionOfficeDto> getAllTransactionOffices();

    List<EmployeeDto> getAllEmployeesOfStorageOffice(Long storageOfficeId);
    List<EmployeeDto> getAllEmployeesOfTransactionOffice(Long transactionOfficeId);

    List<Order> getAllOrdersInStorage(Long storageOfficeId);

}
