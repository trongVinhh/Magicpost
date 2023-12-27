package com.magicpost.circus.service;

import com.magicpost.circus.entity.info.PackageTransfer;

import java.util.List;

public interface StorageEmployeeService {
    public void transferPackageToTransactionOffice(String orderCode, Long transactionOfficeId, Long storageId);
    public List<PackageTransfer> getPackageTransferToStorageOffice(Long storageId);
    public void confirmPackageTransferToStorageOffice(String orderCode, Long storageId);
    public void transferPackageFromStorageToOtherStorage(String orderCode, Long currentStorageId, Long otherStorageId);
}
