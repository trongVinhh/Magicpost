package com.magicpost.circus.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor@NoArgsConstructor
@Getter
@Setter
public class PackageTransferResponse {
    private Long id;
    private String orderCode;
    private String status;
    private String currentStorageName;
    private String currentStorageAddress;
    private Long price;
    private Long mass;
    private String packageType;
    private Long postage;

}
