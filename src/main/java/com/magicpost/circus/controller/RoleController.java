package com.magicpost.circus.controller;

import com.magicpost.circus.entity.role.Role;
import com.magicpost.circus.payload.RoleDto;
import com.magicpost.circus.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRole(@PathVariable Long id) {
        RoleDto role = roleService.getRole(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        RoleDto role = roleService.createRole(roleDto);

        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        RoleDto role = this.roleService.updateRole(id, roleDto);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
