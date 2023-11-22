package com.magicpost.circus.repository;

import com.magicpost.circus.entity.person.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
