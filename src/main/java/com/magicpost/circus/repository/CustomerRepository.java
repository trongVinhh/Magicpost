package com.magicpost.circus.repository;

import com.magicpost.circus.entity.person.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.phone LIKE CONCAT('%',?1,'%')")
    public List<Customer> findByPhone(String phone);
    @Query("SELECT c FROM Customer c WHERE c.firstName LIKE CONCAT('%',?1,'%') OR c.lastName LIKE CONCAT('%',?1,'%')")
    public List<Customer>  findByName(String name);
}
