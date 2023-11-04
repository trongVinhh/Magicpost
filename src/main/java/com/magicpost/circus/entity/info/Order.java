package com.magicpost.circus.entity.info;

import com.magicpost.circus.entity.company.branch.StorageOffice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_storage", nullable = false)
    private StorageOffice currentStorage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transactionId;

    @OneToOne(mappedBy = "orderId", cascade = CascadeType.ALL)
    private Tracking tracking;

}
