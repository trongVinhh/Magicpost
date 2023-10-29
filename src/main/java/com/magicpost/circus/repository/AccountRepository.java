package com.magicpost.circus.repository;

import com.magicpost.circus.entity.info.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>  {
}
