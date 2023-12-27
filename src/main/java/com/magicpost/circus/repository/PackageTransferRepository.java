package com.magicpost.circus.repository;

import com.magicpost.circus.entity.info.PackageTransfer;
import com.magicpost.circus.entity.info.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageTransferRepository extends JpaRepository<PackageTransfer, Long> {
    @Query("SELECT t FROM PackageTransfer t WHERE t.orderCode = ?1")
    public PackageTransfer findByOrderCode(String orderCode);

    @Query("SELECT t FROM PackageTransfer t WHERE t.orderCode = ?1 " +
            "AND t.endOffice = ?2 " +
            "AND (( t.from = 'TransactionOffice To StorageOffice' ) " +
            "OR ( t.from = 'StorageOffice To TransactionOffice' ))")
    public PackageTransfer findPackageSendToStorage(String orderCode, Long storageId);
    @Query("SELECT t FROM PackageTransfer t WHERE t.orderCode = ?1 AND t.endOffice = ?2")
    public PackageTransfer findPackageSendToTransOffice(String orderCode, Long transactionOfficeId);
    @Query("SELECT t FROM PackageTransfer t WHERE t.orderCode = ?1 AND t.endOffice = ?2 AND t.from = 'TransactionOffice To StorageOffice'")
    public PackageTransfer findPackageSendFromTransToStorage(String orderCode, Long storageId);
    @Query("SELECT t FROM PackageTransfer t WHERE t.orderCode = ?1 AND t.endOffice = ?2 AND t.from = 'StorageOffice To TransactionOffice'")
    public PackageTransfer findPackageSendFromStorageToTransactionOffice(String orderCode, Long transactionOfficeId);
    @Query("SELECT t FROM PackageTransfer t WHERE t.orderCode = ?1 AND t.endOffice = ?2 AND t.from = 'StorageOffice To StorageOffice'")
    public PackageTransfer findPackageSendFromStorageToStorage(String orderCode, Long storageId);
    @Query("SELECT t FROM PackageTransfer t WHERE t.endOffice = ?1")
    public List<PackageTransfer> findPackageSendToTransactionOfficeById(Long transactionOfficeId);
    @Query("SELECT t FROM PackageTransfer t WHERE t.endOffice = ?1")
    public List<PackageTransfer> findPackageSendToStorageOfficeById(Long storageId);
}
