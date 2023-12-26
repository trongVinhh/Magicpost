package com.magicpost.circus.service;

import com.magicpost.circus.entity.info.PackageDelivery;
import com.magicpost.circus.entity.info.PackageTransfer;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.payload.OrderDto;
import com.magicpost.circus.payload.TransactionDto;

import java.util.List;

public interface TransactionEmployeeService {
    public TransactionDto createTransaction(TransactionDto transactionDto) throws Exception;
    public TransactionDto updateTransaction(Long transactionId ,TransactionDto transactionDto);
    public TransactionDto getTransaction(Long id);
    public List<TransactionDto> getAllTransactions();

    public void transferPackageToStorage(String orderCode, Long storageId, Long transactionOfficeId);
    // Danh sách package gửi tới gửi lại điểm giao dịch
    public List<PackageTransfer> getPackageTransferToTransactionOffice(Long transactionOfficeId);
    // Xác nhận (đơn) hàng về từ điểm tập kết.
    public void confirmPackageReceived(String orderCode);
    // Tạo đơn hàng cần chuyển đến tay người nhận.
    public void createPackageDelivery(String orderCode);
    public List<PackageDelivery> getPackageDelivery();
    // Xác nhận hàng đã chuyển đến tay người nhận theo .
    public void confirmPackageDelivered(String orderCode);
    // Xác nhận hàng không chuyển được đến người nhận và trả lại điểm giao dịch.
    public void confirmPackageNotDelivered(String orderCode);
    //Thống kê các hàng đã chuyển thành công, các hàng chuyển không thành công và trả lại điểm giao dịch.
    public List<PackageDelivery> statisticPackageTransfer();
}
