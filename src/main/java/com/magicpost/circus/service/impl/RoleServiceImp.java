package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.role.Role;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.RoleDto;
import com.magicpost.circus.repository.RoleRepository;
import com.magicpost.circus.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = this.mapToEntity(roleDto);
        // save
        Role newRole = this.roleRepository.save(role);
        return this.mapToDto(newRole);
    }

    @Override
    public RoleDto getRole(Long id) {
        Role role = this.roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        return this.mapToDto(role);
    }

    @Override
    public void deleteRole(Long id) {

        try {
            this.roleRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Role", "id", id);
        }
    }

    @Override
    public RoleDto updateRole(Long id ,RoleDto roleDto) {
        Optional<Role> role = Optional.ofNullable(this.roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id)));
        Role roleUpdated = new Role();
        if (role.isPresent()) {
            role.get().setName(roleDto.getName());
            roleUpdated = this.roleRepository.save(role.get());
        }

        return this.mapToDto(roleUpdated);
    }

    @Override
    public List<Role> getRoles() {
        return this.roleRepository.findAll();
    }


    private Role mapToEntity(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());
        return role;
    }

    private RoleDto mapToDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }

}
