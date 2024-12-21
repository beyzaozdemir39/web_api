package com.example.web_api.service;

import com.example.web_api.entities.Role;
import com.example.web_api.exception.ResourceNotFoundException;
import com.example.web_api.repos.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Role saveRole(Role role) {
        if (roleRepository.findByName(role.getName()).isPresent()) {
            throw new RuntimeException("Role already exists");
        }
        return roleRepository.save(role);
    }
    public Role updateRole(Long id, Role updatedRole) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + id + " not found"));

        existingRole.setName(updatedRole.getName());
        return roleRepository.save(existingRole);
    }
    public void deleteRole(Long id) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + id + " not found"));

        roleRepository.delete(existingRole);
    }
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
