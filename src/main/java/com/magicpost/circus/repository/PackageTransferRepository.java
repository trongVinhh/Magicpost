package com.magicpost.circus.repository;

import com.magicpost.circus.entity.info.PackageTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PackageTransferRepository extends JpaRepository<PackageTransfer, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.orderCode = ?1")
    public PackageTransfer findByOrderCode(String orderCode);
}
