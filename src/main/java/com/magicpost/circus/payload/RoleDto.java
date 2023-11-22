package com.magicpost.circus.payload;

import com.magicpost.circus.entity.person.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto {
    private Long id;
    private String name;
    private List<Employee> employees;
}
