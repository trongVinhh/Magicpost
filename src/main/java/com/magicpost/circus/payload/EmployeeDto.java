package com.magicpost.circus.payload;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String username;
    private String password;
    private String address;
    private Set<Role> role;
}
