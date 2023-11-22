package com.magicpost.circus.controller;

import com.magicpost.circus.payload.EmployeeDto;
import com.magicpost.circus.payload.StorageOfficeDto;
import com.magicpost.circus.payload.TransactionOfficeDto;
import com.magicpost.circus.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_MANAGER_STORAGE')")
    @PutMapping("/setStorageForEmployee/{storageId}/employee/{employeeId}")
    public ResponseEntity<StorageOfficeDto> setStorageOfficeForEmployee(@PathVariable Long employeeId, @PathVariable Long storageId) {
        StorageOfficeDto storageOfficeDto = this.managerService.setStorageOfficeForEmployee(employeeId, storageId);
        return new ResponseEntity<>(storageOfficeDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_MANAGER_TRANSACTION')")
    @PutMapping("/setTransactionForEmployee/{transactionId}/employee/{employeeId}")
    public ResponseEntity<TransactionOfficeDto> setTransactionOfficeForEmployee(@PathVariable Long employeeId, @PathVariable Long transactionId) {
        TransactionOfficeDto transactionOfficeDto = this.managerService.setTransactionOfficeForEmployee(employeeId, transactionId);
        return new ResponseEntity<>(transactionOfficeDto, HttpStatus.OK);
    }
}
