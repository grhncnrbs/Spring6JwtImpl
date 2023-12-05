package com.grhncnrbs.fids.controller;

import com.grhncnrbs.fids.models.Role;
import com.grhncnrbs.fids.repository.RoleRepository;

import com.grhncnrbs.fids.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserManageService userManageService;


    @GetMapping("/role")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return userManageService.readAllUsers();
    }

    @DeleteMapping("/delete-user/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        return userManageService.deleteUser(userId);
    }
}
