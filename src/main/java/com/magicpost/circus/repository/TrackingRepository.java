package com.magicpost.circus.repository;

import com.magicpost.circus.entity.info.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
}
