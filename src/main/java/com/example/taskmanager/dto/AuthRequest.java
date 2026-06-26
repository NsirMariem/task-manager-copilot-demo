package com.example.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Authentication request payload")
public class AuthRequest {

    @Schema(description = "Username", example = "user1", required = true)
    @NotBlank(message = "Username must not be blank")
    private String username;

    @Schema(description = "Password", example = "password123", required = true)
    @NotBlank(message = "Password must not be blank")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
