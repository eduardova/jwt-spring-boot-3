package dev.eduardova.jwt.controllers;

import dev.eduardova.jwt.services.impl.UserDetailsServiceImpl;
import dev.eduardova.jwt.components.JwtUtils;
import dev.eduardova.jwt.dtos.auth.AuthenticationRequest;
import dev.eduardova.jwt.dtos.auth.AuthenticationToken;
import dev.eduardova.jwt.exceptions.GeneralInputErrorException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    @PostMapping("${configs.security.uri-access-token}")
    public ResponseEntity<AuthenticationToken> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword(), Collections.emptyList())
            );
            var user = userDetailsService.loadUserByUsername(request.getEmail());
            if (user == null) {
                throw new UsernameNotFoundException("User %s not found".formatted(request.getEmail()));
            }
            var jwt = jwtUtils.generateToken(user);
            return ResponseEntity.ok(jwt);
        }
        catch (AuthenticationException e) {
            throw new GeneralInputErrorException("Authentication error");
        }
    }

    @PostMapping("${configs.security.uri-refresh-token}")
    public ResponseEntity<AuthenticationToken> authenticate(@RequestBody String refreshToken) {
        return ResponseEntity.ok(jwtUtils.refreshToken(refreshToken));
    }

}
