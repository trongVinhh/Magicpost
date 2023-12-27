package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.entity.info.PackageTransfer;
import com.magicpost.circus.entity.info.Tracking;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.exception.MagicPostException;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.repository.*;
import com.magicpost.circus.service.StorageEmployeeService;
import com.magicpost.circus.ultis.PackageTransferStatus;
import org.springframework.http.HttpStatus;
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

    // tạo đơn chuyển hàng từ kho đến điểm giao dịch
    @Override
    public void transferPackageToTransactionOffice(String orderCode, Long transactionOfficeId, Long storageId) {
        List<PackageTransfer> packageTransfers = this.packageTransferRepository.findAll();
        packageTransfers.forEach(packageTransfer -> {
            if (packageTransfer.getOrderCode().equals(orderCode)) {
                throw new MagicPostException( HttpStatus.BAD_REQUEST, "Package Transferring");
            }
        });
        Transaction transaction = this.transactionRepository.findByOrderCode(orderCode);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction", orderCode);
        }
        PackageTransfer packageTransfer = new PackageTransfer();
        packageTransfer.setOrderCode(orderCode);
        packageTransfer.setFrom(PackageTransferStatus.StorageOfficeToTransaction);
        packageTransfer.setStartOffice(storageId);
        packageTransfer.setEndOffice(transactionOfficeId);
        this.packageTransferRepository.save(packageTransfer);
    }

    // danh sách đơn chuyển hàng từ điểm giao dịch tới kho
    @Override
    public List<PackageTransfer> getPackageTransferToStorageOffice(Long storageId) {
        return this.packageTransferRepository.findPackageSendToStorageOfficeById(storageId);
    }

    // chuyển đơn hàng từ kho này sang kho khác
    @Override
    public void transferPackageFromStorageToOtherStorage(String orderCode, Long currentStorageId, Long otherStorageId) {
        List<PackageTransfer> packageTransfers = this.packageTransferRepository.findAll();
        packageTransfers.forEach(packageTransfer -> {
            if (packageTransfer.getOrderCode().equals(orderCode)) {
                throw new MagicPostException( HttpStatus.BAD_REQUEST, "Package Transferring");
            }
        });
        Transaction transaction = this.transactionRepository.findByOrderCode(orderCode);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction", orderCode);
        }
        PackageTransfer packageTransfer = new PackageTransfer();
        packageTransfer.setOrderCode(orderCode);
        packageTransfer.setFrom(PackageTransferStatus.StorageOfficeToStorageOffice);
        packageTransfer.setStartOffice(currentStorageId);
        packageTransfer.setEndOffice(otherStorageId);
        this.packageTransferRepository.save(packageTransfer);
    }

    // xác nhận đơn chuyển hàng tới kho
    @Override
    public void confirmPackageTransferToStorageOffice(String orderCode, Long storageId) {
        PackageTransfer packageTransfer = this.packageTransferRepository.findPackageSendToStorage(orderCode, storageId);
        if (packageTransfer == null) {
            throw new ResourceNotFoundException("PackageTransfer", orderCode);
        }
        Transaction transaction = this.transactionRepository.findByOrderCode(orderCode);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction", orderCode);
        }
        Order order = transaction.getOrder();
        StorageOffice storageOffice = this.storageOfficeRepository.findById(packageTransfer.getEndOffice())
                .orElseThrow(() -> new ResourceNotFoundException("StorageOffice", "id", packageTransfer.getEndOffice()));
        order.setCurrentStorage(storageOffice);
        Tracking tracking = order.getTracking();
        tracking.setStatus("Đang chuyển kho");
        this.orderRepository.save(order);
        this.packageTransferRepository.delete(packageTransfer);
    }


}
