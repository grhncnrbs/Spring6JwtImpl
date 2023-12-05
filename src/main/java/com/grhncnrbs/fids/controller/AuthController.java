package com.grhncnrbs.fids.controller;

import com.grhncnrbs.fids.dto.LoginRequest;
import com.grhncnrbs.fids.dto.LoginResponse;
import com.grhncnrbs.fids.dto.RegisterRequest;
import com.grhncnrbs.fids.service.AuthenticationService;
import com.grhncnrbs.fids.service.UserManageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserManageService userManageService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticateLoginRequest(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return userManageService.create(request);
    }
}
