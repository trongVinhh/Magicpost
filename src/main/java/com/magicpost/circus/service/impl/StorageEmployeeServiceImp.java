package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.entity.info.PackageTransfer;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.repository.*;
import com.magicpost.circus.service.StorageEmployeeService;
import com.magicpost.circus.ultis.PackageTransferStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageEmployeeServiceImp implements StorageEmployeeService {
    private PackageTransferRepository packageTransferRepository;
    private TransactionRepository transactionRepository;
    private TransactionOfficeRepository transactionOfficeRepository;
    private StorageOfficeRepository storageOfficeRepository;
    private OrderRepository orderRepository;

    public StorageEmployeeServiceImp(TransactionRepository transactionRepository,
                                     TransactionOfficeRepository transactionOfficeRepository,
                                     StorageOfficeRepository storageOfficeRepository,
                                     OrderRepository orderRepository,
                                     PackageTransferRepository packageTransferRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionOfficeRepository = transactionOfficeRepository;
        this.storageOfficeRepository = storageOfficeRepository;
        this.orderRepository = orderRepository;
        this.packageTransferRepository = packageTransferRepository;
    }

    @Override
    public void transferPackageToTransactionOffice(String orderCode, Long transactionOfficeId, Long storageId) {
        List<PackageTransfer> packageTransfers = this.packageTransferRepository.findAll();
        packageTransfers.forEach(packageTransfer -> {
            if (packageTransfer.getOrderCode().equals(orderCode)) {
                return;
            }
        });
        PackageTransfer packageTransfer = new PackageTransfer();
        packageTransfer.setOrderCode(orderCode);
        packageTransfer.setFrom(PackageTransferStatus.StorageOfficeToTransaction);
        packageTransfer.setStorageId(storageId);
        packageTransfer.setTransactionOfficeId(transactionOfficeId);
        this.packageTransferRepository.save(packageTransfer);
    }

    @Override
    public List<PackageTransfer> getPackageTransferToStorageOffice(Long storageId) {
        return this.packageTransferRepository.findByStorageId(storageId);
    }

    @Override
    public void confirmPackageTransferToStorageOffice(String orderCode) {
        PackageTransfer packageTransfer = this.packageTransferRepository.findByOrderCode(orderCode);
        if (packageTransfer == null) {
            throw new ResourceNotFoundException("PackageTransfer", orderCode);
        }
        Transaction transaction = this.transactionRepository.findByOrderCode(orderCode);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction", orderCode);
        }
        Order order = transaction.getOrder();
        StorageOffice storageOffice = this.storageOfficeRepository.findById(packageTransfer.getStorageId())
                .orElseThrow(() -> new ResourceNotFoundException("StorageOffice", "id", packageTransfer.getStorageId()));
        order.setCurrentStorage(storageOffice);

        this.orderRepository.save(order);
        this.packageTransferRepository.delete(packageTransfer);
    }
}
