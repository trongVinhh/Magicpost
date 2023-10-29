package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.role.Role;
import com.magicpost.circus.payload.RoleDto;
import com.magicpost.circus.repository.RoleRepository;
import com.magicpost.circus.service.RoleService;
import org.modelmapper.ModelMapper;
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
    public Role createRole(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());
        // save
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(Long id) {
        Optional<Role> role = this.roleRepository.findById(id);
        return role.orElse(null);
    }

    @Override
    public void deleteRole(Long id) {
        this.roleRepository.deleteById(id);
    }

    @Override
    public Role updateRole(Long id ,RoleDto roleDto) {
        Optional<Role> role = Optional.ofNullable(this.roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Not exist")));
        Role roleUpdated = new Role();
        if (role.isPresent()) {
            role.get().setName(roleDto.getName());
            roleUpdated = this.roleRepository.save(role.get());
        }

        return roleUpdated;
    }

    @Override
    public List<Role> getRoles() {
        return this.roleRepository.findAll();
    }

}
