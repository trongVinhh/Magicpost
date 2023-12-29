package com.magicpost.circus.payload;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.info.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class OrderDto {
    private Long id;
    private String currentStorageName;
    private String currentStorageAddress;
    private TransactionDto transaction;
    private String status;
}
