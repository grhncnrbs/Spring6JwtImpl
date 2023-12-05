package com.grhncnrbs.fids.service;

import com.grhncnrbs.fids.dto.RegisterRequest;
import com.grhncnrbs.fids.dto.ResponseDTO;
import com.grhncnrbs.fids.dto.UserResponse;
import com.grhncnrbs.fids.exceptions.CustomException;
import com.grhncnrbs.fids.models.Role;
import com.grhncnrbs.fids.models.User;
import com.grhncnrbs.fids.repository.RoleRepository;
import com.grhncnrbs.fids.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserManageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserManageService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;

    public ResponseEntity<?> create(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername().trim())) {
            if (userRepository.existsByEmail(registerRequest.getEmail().trim())) {
                return ResponseDTO.errorResponse("Error: Email is already taken!");
            }
            return ResponseDTO.errorResponse("Error: Username is already taken!");
        }
        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()));
        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            String roleName = role.trim().toUpperCase();
            Role verifiedRole = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new CustomException("Error: " + roleName + " is not found."));
            roles.add(verifiedRole);
        });
        user.setRoles(roles);
        userRepository.save(user);
        LOGGER.info("{} Created new user {}", readAuthenticatedUserName(), user.getUsername());
        return ResponseDTO.successResponse("User created successfully!", null);
    }

    public String readAuthenticatedUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String user = "";
        if (principal instanceof UserDetails) {
            user = ((UserDetails) principal).getUsername();
        }
        return user;
    }

    public ResponseEntity<?> readAllUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
//                .map(user->new UserResponse(user.getId(),user.getUsername(),user.getEmail(),user.getRoles()))
                .collect(Collectors.toList());
        return ResponseDTO.successResponse("Users fetched successfully!", users);
    }

    public ResponseEntity<?> deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("Error: User not found for this id : " + id));
        userRepository.delete(user);
        LOGGER.info("{} deleted user {}", readAuthenticatedUserName(), user.getUsername());
        return ResponseDTO.successResponse("User deleted successfully!");
    }
}
