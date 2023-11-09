package com.magicpost.circus.repository.employee;

import com.magicpost.circus.entity.person.child.ManagerStorage;
import com.magicpost.circus.entity.person.child.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerStorageRepository extends JpaRepository<ManagerStorage, Long> {
}
