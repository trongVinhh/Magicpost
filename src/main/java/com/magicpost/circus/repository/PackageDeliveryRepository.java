package com.magicpost.circus.repository;

import com.magicpost.circus.entity.info.PackageDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PackageDeliveryRepository extends JpaRepository<PackageDelivery, Long> {
    @Query("SELECT p FROM PackageDelivery p WHERE p.orderCode = ?1")
    public PackageDelivery findByOrderCode(String orderCode);
}
