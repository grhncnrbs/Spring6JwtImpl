package com.grhncnrbs.fids.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import java.util.Set;


@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "Email is required.")
    @Size(max = 50)
    @Email
    private String email;

    @NotEmpty(message = "Role is required.")
    private Set<String> role;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 200)
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

}
