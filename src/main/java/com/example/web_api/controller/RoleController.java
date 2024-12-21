package com.example.web_api.controller;

import com.example.web_api.entities.Role;
import com.example.web_api.exception.ResourceNotFoundException;
import com.example.web_api.service.RoleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private final RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        logger.info("Fetching all roles");
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        logger.info("Fetching role with ID: {}", id);
        Role role = roleService.getRoleById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + id + " not found"));
        return ResponseEntity.ok(role);
    }
    @PostMapping
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) {
        logger.info("Creating new role: {}", role);
        if (roleService.findByName(role.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Role createdRole = roleService.saveRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @Valid @RequestBody Role role) {
        logger.info("Updating role with ID: {}", id);
        Role updatedRole = roleService.updateRole(id, role);
        return ResponseEntity.ok(updatedRole);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        logger.info("Deleting role with ID: {}", id);
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
