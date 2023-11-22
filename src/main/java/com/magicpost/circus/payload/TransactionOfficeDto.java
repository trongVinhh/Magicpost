package com.magicpost.circus.payload;

import com.magicpost.circus.entity.person.Employee;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionOfficeDto {
    private Long id;
    private String name;
    private String address;
    private String hotline;
    private String email;
    private List<Employee> employees;
}
