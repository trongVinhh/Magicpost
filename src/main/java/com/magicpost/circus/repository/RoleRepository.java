package com.magicpost.circus.repository;

import com.magicpost.circus.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
