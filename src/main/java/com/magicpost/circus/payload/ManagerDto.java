package com.magicpost.circus.payload;

import jakarta.persistence.Column;

public class ManagerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private AccountDto accountDto;
}
