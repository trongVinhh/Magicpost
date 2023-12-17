package com.magicpost.circus.service;

import com.magicpost.circus.payload.InfoUserResponse;
import com.magicpost.circus.payload.StorageOfficeDto;
import com.magicpost.circus.payload.TransactionOfficeDto;

public interface ManagerService {
    StorageOfficeDto setStorageOfficeForEmployee(Long employeeId, Long storageOfficeId);
    TransactionOfficeDto setTransactionOfficeForEmployee(Long employeeId, Long transactionOfficeId);

    InfoUserResponse getStorageIdFromUsername(String username);
    InfoUserResponse getTransactionOfficeIdFromUsername(String username);

}
