package com.magicpost.circus.repository;

import com.magicpost.circus.entity.info.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.orderCode = ?1")
    public Transaction findByOrderCode(String orderCode);
}
