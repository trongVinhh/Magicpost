package com.magicpost.circus.entity.company.branch;

import com.magicpost.circus.entity.company.HeadOffice;
import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.entity.info.Transaction;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.entity.person.child.Manager;

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
@Table(name = "storage_office")
public class StorageOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager managerId;

    @OneToMany(mappedBy = "storageOffice", cascade = CascadeType.ALL)
    private List<Employee> employees;
    @OneToMany(mappedBy = "currentStorage", cascade = CascadeType.ALL)
    private List<Order> orders;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "head_office_id", referencedColumnName = "id")
    private HeadOffice headOffice;
}
