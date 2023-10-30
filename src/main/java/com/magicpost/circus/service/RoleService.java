package com.magicpost.circus.service;

import com.magicpost.circus.entity.role.Role;
import com.magicpost.circus.payload.RoleDto;

import java.util.List;

public interface RoleService {
    public RoleDto createRole(RoleDto roleDto);
    public RoleDto getRole(Long id);
    public void deleteRole(Long id);
    public RoleDto updateRole(Long id, RoleDto roleDto);
    public List<Role> getRoles();
}
