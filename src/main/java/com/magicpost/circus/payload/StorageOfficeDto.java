package com.magicpost.circus.payload;

import com.magicpost.circus.entity.info.Order;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.entity.person.child.ManagerStorage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class StorageOfficeDto {
    private Long id;
    private String name;
    private String address;
    private List<Employee> employees;
}
