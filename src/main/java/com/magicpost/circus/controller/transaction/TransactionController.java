package com.magicpost.circus.controller.transaction;

import com.magicpost.circus.payload.TransactionDto;
import com.magicpost.circus.service.TransactionEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Autowired
    private TransactionEmployeeService transactionEmployeeService;

    public TransactionController(TransactionEmployeeService transactionEmployeeService) {
        this.transactionEmployeeService = transactionEmployeeService;
    }

    @PostMapping("/employee/{employeeId}/transaction-office/{transactionOfficeId}/storage-office/{storageOfficeId}")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto,
                                                            @PathVariable Long employeeId,
                                                            @PathVariable Long transactionOfficeId,
                                                            @PathVariable Long storageOfficeId) {
        TransactionDto dto = this.transactionEmployeeService.createTransaction(transactionDto, employeeId, transactionOfficeId, storageOfficeId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
