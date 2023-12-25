package com.magicpost.circus.repository;

import com.magicpost.circus.entity.info.PackageTransfer;
import com.magicpost.circus.entity.info.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageTransferRepository extends JpaRepository<PackageTransfer, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.orderCode = ?1")
    public Transaction findTransactionByOrderCode(String orderCode);
    @Query("SELECT t FROM PackageTransfer t WHERE t.orderCode = ?1")
    public PackageTransfer findByOrderCode(String orderCode);
    @Query("SELECT t FROM PackageTransfer t WHERE t.transactionOfficeId = ?1 AND t.from = 'StorageOffice To TransactionOffice'")
    public List<PackageTransfer> findByTransactionOfficeId(Long transactionOfficeId);
    @Query("SELECT t FROM PackageTransfer t WHERE t.storageId = ?1 AND t.from = 'TransactionOffice To StorageOffice'")
    public List<PackageTransfer> findByStorageId(Long storageId);
}
