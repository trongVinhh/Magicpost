package com.magicpost.circus.service;

import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.payload.EmployeeDto;
import com.magicpost.circus.payload.StorageOfficeDto;
import com.magicpost.circus.payload.TransactionOfficeDto;

public interface ManagerService {
    StorageOfficeDto setStorageOfficeForEmployee(Long employeeId, Long storageOfficeId);
    TransactionOfficeDto setTransactionOfficeForEmployee(Long employeeId, Long transactionOfficeId);

}
