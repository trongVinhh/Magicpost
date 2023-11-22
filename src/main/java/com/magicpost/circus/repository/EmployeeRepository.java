package com.magicpost.circus.repository;

import com.magicpost.circus.entity.person.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUsernameOrEmail(String username, String email);
    Optional<Employee> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
