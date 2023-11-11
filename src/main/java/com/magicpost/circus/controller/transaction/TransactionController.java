package com.magicpost.circus.controller.transaction;

import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.payload.TransactionDto;
import com.magicpost.circus.service.TransactionEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Autowired
    private TransactionEmployeeService transactionEmployeeService;

    public TransactionController(TransactionEmployeeService transactionEmployeeService) {
        this.transactionEmployeeService = transactionEmployeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        TransactionDto dto = this.transactionEmployeeService.createTransaction(transactionDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable Long id) {
        TransactionDto transaction = this.transactionEmployeeService.getTransaction(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id,
                                                         @RequestBody TransactionDto transactionDto) {
        TransactionDto dto = this.transactionEmployeeService.updateTransaction(id, transactionDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = this.transactionEmployeeService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
