package com.magicpost.circus.repository;

import com.magicpost.circus.entity.info.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
