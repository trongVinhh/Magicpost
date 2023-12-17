package com.magicpost.circus.entity.info;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "package_transfer")
public class PackageTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_code")
    private String orderCode;
    @Column(name = "from_")
    private String from;
    @Column(name = "storage_id")
    private Long storageId;
    @Column(name = "trans_office_id")
    private Long transactionOfficeId;
}
