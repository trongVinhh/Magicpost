package com.magicpost.circus.entity.person;

import com.magicpost.circus.entity.info.Account;
import com.magicpost.circus.entity.role.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;

    public Role role;

}
