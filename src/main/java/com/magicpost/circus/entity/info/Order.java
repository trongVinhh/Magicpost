package com.magicpost.circus.entity.info;

import com.magicpost.circus.entity.company.branch.StorageOffice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private String orderCode;
    private String receiveAddress;
    private String name;
    private String phoneNumber;

    private StorageOffice currentStorage;
    private Order orderId;

}
