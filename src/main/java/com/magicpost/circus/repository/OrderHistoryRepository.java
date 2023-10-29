package com.magicpost.circus.repository;

import com.magicpost.circus.entity.info.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
}
