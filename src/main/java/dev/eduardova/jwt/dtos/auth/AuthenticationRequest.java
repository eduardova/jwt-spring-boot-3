package dev.eduardova.jwt.dtos.auth;

import lombok.Data;

@Data
public class AuthenticationRequest {
    
    private String email;
    private String password;
}
