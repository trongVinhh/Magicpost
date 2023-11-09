package com.magicpost.circus.repository.employee;

import com.magicpost.circus.entity.person.child.EmployeeTransaction;
import com.magicpost.circus.entity.person.child.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeTransactionRepository extends JpaRepository<EmployeeTransaction, Long> {
}
