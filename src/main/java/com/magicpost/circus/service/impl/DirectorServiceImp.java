package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Account;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.service.DirectorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServiceImp implements DirectorService {
    @Override
    public List<StorageOffice> getAllStorageOffices() {
        return null;
    }

    @Override
    public List<TransactionOffice> getAllTransactionOffices() {
        return null;
    }

    @Override
    public Account grantAccountForManager(Long managerId) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }
}
