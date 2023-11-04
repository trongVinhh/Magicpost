package com.magicpost.circus.service;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Account;
import com.magicpost.circus.entity.info.Order;

import java.util.List;

public interface DirectorService {
    List<StorageOffice> getAllStorageOffices();
    List<TransactionOffice> getAllTransactionOffices();
    Account grantAccountForManager(Long managerId);

    List<Order> getAllOrders();

}
