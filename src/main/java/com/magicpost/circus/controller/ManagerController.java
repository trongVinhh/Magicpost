package com.magicpost.circus.controller;

import com.magicpost.circus.payload.InfoUserResponse;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_STORAGE')")
    @PutMapping("/setStorageForEmployee/{storageId}/employee/{employeeId}")
    public ResponseEntity<StorageOfficeDto> setStorageOfficeForEmployee(@PathVariable Long employeeId, @PathVariable Long storageId) {
        StorageOfficeDto storageOfficeDto = this.managerService.setStorageOfficeForEmployee(employeeId, storageId);
        return new ResponseEntity<>(storageOfficeDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_TRANSACTION')")
    @PutMapping("/setTransactionForEmployee/{transactionId}/employee/{employeeId}")
    public ResponseEntity<TransactionOfficeDto> setTransactionOfficeForEmployee(@PathVariable Long employeeId, @PathVariable Long transactionId) {
        TransactionOfficeDto transactionOfficeDto = this.managerService.setTransactionOfficeForEmployee(employeeId, transactionId);
        return new ResponseEntity<>(transactionOfficeDto, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_STORAGE')")
    @GetMapping("/getStorageIdByUsername")
    public ResponseEntity<InfoUserResponse> getStorageIdByUsername(@RequestParam("username") String username) {
        InfoUserResponse info = this.managerService.getStorageIdFromUsername(username);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_TRANSACTION', 'ROLE_EMPLOYEE_TRANSACTION')")
    @GetMapping("/getTransactionOfficeIdByUsername")
    public ResponseEntity<InfoUserResponse> getTransactionOfficeIdByUsername(@RequestParam("username") String username) {
        InfoUserResponse info = this.managerService.getTransactionOfficeIdFromUsername(username);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_STORAGE')")
    @GetMapping("/getStorage")
    public ResponseEntity<StorageOfficeDto> getStorageInfoFromId(@RequestParam("id") String id) {
        try {
            Long storageId = Long.parseLong(id);
            StorageOfficeDto storageOfficeDto = this.managerService.getStorageById(storageId);
            return new ResponseEntity<>(storageOfficeDto, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_TRANSACTION', 'ROLE_EMPLOYEE_TRANSACTION')")
    @GetMapping("/getTransactionOffice")
    public ResponseEntity<TransactionOfficeDto> getTransactionOfficeInfoFromId(@RequestParam("id") String id) {
        try {
            Long storageId = Long.parseLong(id);
            TransactionOfficeDto transactionOfficeDto = this.managerService.getTransactionOfficeById(storageId);
            return new ResponseEntity<>(transactionOfficeDto, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
