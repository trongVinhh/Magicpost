package com.magicpost.circus.entity.company;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.magicpost.circus.entity.company.branch.StorageOffice;
import com.magicpost.circus.entity.company.branch.TransactionOffice;

import java.util.List;

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
@Table(name = "head_office")
public class HeadOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "director_name")
    private String directorName;
    @OneToMany(mappedBy = "headOffice")
    @JsonManagedReference
    private List<TransactionOffice> transactionOffices;
    @OneToMany(mappedBy = "headOffice")
    @JsonManagedReference
    private List<StorageOffice> storageOffices;

}
