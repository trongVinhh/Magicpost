package com.magicpost.circus.controller.employee;

import com.magicpost.circus.payload.OrderDto;
import com.magicpost.circus.service.TransactionEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequestMapping("/api/v1/employee-transaction")
public class EmplTransactionController {

    @Autowired
    private TransactionEmployeeService transactionEmployeeService;

    @PostMapping("/sendPackageToStorage")
    public ResponseEntity<String> sendPackageToWarehouse(@RequestParam("orderCode") String orderCode,
                                                         @RequestParam("storageId") Long storageId,
                                                         @RequestParam("transactionOfficeId") Long transactionOfficeId) {
        this.transactionEmployeeService.transferPackageToStorage(orderCode, storageId, transactionOfficeId);
        return new ResponseEntity<>("Package was sent to storage", HttpStatus.OK);
    }
}
