package com.magicpost.circus.controller.transaction;

import com.magicpost.circus.entity.info.Transaction;
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

    @PostMapping("/employee/{employeeId}/transaction-office/{transactionOfficeId}")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDto transactionDto,
                                                         @PathVariable Long employeeId,
                                                         @PathVariable Long transactionOfficeId
                                                         ) {
        Transaction transaction = this.transactionEmployeeService.createTransaction(transactionDto, employeeId, transactionOfficeId);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        Transaction transaction = this.transactionEmployeeService.getTransaction(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PutMapping("/{id}/employee/{employeeId}/transaction-office/{transactionOfficeId}/storage-office/{storageOfficeId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id,
                                                         @RequestBody TransactionDto transactionDto,
                                                         @PathVariable Long employeeId,
                                                         @PathVariable Long transactionOfficeId,
                                                         @PathVariable Long storageOfficeId) {
        Transaction transaction = this.transactionEmployeeService.updateTransaction(id, transactionDto, employeeId, transactionOfficeId, storageOfficeId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
