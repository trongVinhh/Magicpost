package com.magicpost.circus.entity.company;

import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeadOffice {
    private Long id;
    private String directorName;
    private List<TransactionOffice> transactionOffices;
    private List<StorageOffice> storageOffices;

}
