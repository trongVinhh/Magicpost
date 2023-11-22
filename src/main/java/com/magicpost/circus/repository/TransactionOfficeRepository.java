package com.magicpost.circus.repository;

import com.magicpost.circus.entity.company.branch.TransactionOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionOfficeRepository extends JpaRepository<TransactionOffice, Long> {
}
