package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.info.Account;
import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.entity.role.Role;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.AccountDto;
import com.magicpost.circus.payload.CustomerDto;
import com.magicpost.circus.repository.AccountRepository;
import com.magicpost.circus.repository.EmployeeRepository;
import com.magicpost.circus.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public AccountServiceImp(AccountRepository accountRepository, EmployeeRepository employeeRepository) {
        this.accountRepository = accountRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = this.mapToEntity(accountDto);

        // save to db
        Account temp = this.accountRepository.save(account);
        return this.mapToDto(temp);
    }

    @Override
    public AccountDto getAccount(Long id) {
        Account account = this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        return this.mapToDto(account);
    }

    @Override
    public void deleteAccount(Long id) {
        try {
            this.accountRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Account", "id", id);
        }
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Optional<Account> account = Optional.ofNullable(this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id)));
        Account accountUpdated = new Account();
        if (account.isPresent()) {
            account.get().setUserName(accountDto.getUsername());
            account.get().setPassword(accountDto.getPassword());
            accountUpdated = this.accountRepository.save(account.get());
        }

        return this.mapToDto(accountUpdated);
    }

    @Override
    public List<AccountDto> getAccounts() {
        List<AccountDto> accountDtos = new ArrayList<>();
        List<Account> customer = this.accountRepository.findAll();
        accountDtos = customer.stream().map(this::mapToDto).collect(Collectors.toList());
        return accountDtos;
    }

    private Account mapToEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setUserName(accountDto.getUsername());
        account.setPassword(accountDto.getPassword());
        return account;
    }

    private AccountDto mapToDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setUsername(account.getUserName());
        accountDto.setPassword(account.getPassword());

        return accountDto;
    }
}
