package com.magicpost.circus.controller;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.payload.OrderDto;
import com.magicpost.circus.payload.StorageOfficeDto;
import com.magicpost.circus.payload.TransactionOfficeDto;
import com.magicpost.circus.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/director")
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }


    @GetMapping("/storage-offices")
    public ResponseEntity<List<StorageOfficeDto>> getStorageOffices() {
        List<StorageOfficeDto> storageOfficeDtos = this.directorService.getAllStorageOffices();
        return new ResponseEntity<>(storageOfficeDtos, HttpStatus.OK);
    }

    @GetMapping("/transaction-offices")
    public ResponseEntity<List<TransactionOfficeDto>> getAllTransactionOffices() {
        List<TransactionOfficeDto> transactionOfficeDtos = this.directorService.getAllTransactionOffices();
        return new ResponseEntity<>(transactionOfficeDtos, HttpStatus.OK);
    }

    @GetMapping("/storage/{storageOfficeId}/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders(@PathVariable Long storageOfficeId) {
        List<OrderDto> orders = this.directorService.getAllOrdersInStorage(storageOfficeId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
