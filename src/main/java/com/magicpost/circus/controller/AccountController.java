package com.magicpost.circus.controller;

import com.magicpost.circus.payload.AccountDto;
import com.magicpost.circus.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(this.accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        return new ResponseEntity<>(this.accountService.getAccount(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccounts() {
        return new ResponseEntity<>(this.accountService.getAccounts(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(Long id) {
        return new ResponseEntity<>("Account id: " + id + " was deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updatedAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(this.accountService.updateAccount(id, accountDto), HttpStatus.OK);
    }
}
