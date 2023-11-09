package com.magicpost.circus.repository.employee;

import com.magicpost.circus.entity.person.child.EmployeeStorage;
import com.magicpost.circus.entity.person.child.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeStorageRepository extends JpaRepository<EmployeeStorage, Long> {
}
