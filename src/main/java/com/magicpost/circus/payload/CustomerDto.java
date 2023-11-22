package com.magicpost.circus.payload;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.entity.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
}
