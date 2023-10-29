package com.magicpost.circus.controller;

import com.magicpost.circus.entity.role.Role;
import com.magicpost.circus.payload.RoleDto;
import com.magicpost.circus.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        Role role = roleService.getRole(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleDto roleDto) {
        Role role = roleService.createRole(roleDto);

        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        Role role = this.roleService.updateRole(id, roleDto);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        this.roleService.deleteRole(id);
        return new ResponseEntity<>("Role was deleted", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = this.roleService.getRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
