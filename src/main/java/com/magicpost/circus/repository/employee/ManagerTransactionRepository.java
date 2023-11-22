package com.magicpost.circus.repository.employee;

import com.magicpost.circus.entity.person.child.ManagerTransaction;
import com.magicpost.circus.entity.person.child.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerTransactionRepository extends JpaRepository<ManagerTransaction, Long> {
}
