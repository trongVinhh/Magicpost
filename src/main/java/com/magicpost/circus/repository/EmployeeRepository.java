package com.magicpost.circus.repository;

import com.magicpost.circus.entity.person.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUsernameOrEmail(String username, String email);
    Optional<Employee> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.username LIKE CONCAT('%',?1,'%') " +
            "OR e.firstName LIKE CONCAT('%',?1,'%') " +
            "OR e.lastName LIKE CONCAT('%',?1,'%') " +
            "OR e.email LIKE CONCAT('%',?1,'%') " +
        "OR e.phone LIKE CONCAT('%',?1,'%') ")
    List<Employee> findByUsernameContaining(String keyword);
}
