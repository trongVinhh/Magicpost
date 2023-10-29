package com.magicpost.circus.repository;

import com.magicpost.circus.entity.person.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
