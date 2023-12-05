package com.grhncnrbs.fids.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Data.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    public String userAccess() {
        return "User Data.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminAccess() {
        return "Admin Data.";
    }

    @GetMapping("/super_admin")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public String superAdminAccess() {
        return "Super Admin Data.";
    }
    @GetMapping("/no_auth")
    public String noAuthAccess() {
        return "No Auth Required Data.";
    }
}