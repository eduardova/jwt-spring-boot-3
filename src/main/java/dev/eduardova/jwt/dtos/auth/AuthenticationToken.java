package dev.eduardova.jwt.dtos.auth;

import lombok.Data;

@Data
public class AuthenticationToken {
    
    private String email;
    private String accessToken;
    private String refreshToken;
    
}
