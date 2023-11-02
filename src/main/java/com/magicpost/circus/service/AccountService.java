package com.magicpost.circus.service;

import com.magicpost.circus.entity.info.Account;
import com.magicpost.circus.payload.AccountDto;

import java.util.List;

public interface AccountService {
    public AccountDto createAccount(AccountDto accountDto);
    public AccountDto getAccount(Long id);
    public void deleteAccount(Long id);
    public AccountDto updateAccount(Long id, AccountDto accountDto);
    public List<AccountDto> getAccounts();
}
