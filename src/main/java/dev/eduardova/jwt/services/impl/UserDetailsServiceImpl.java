package dev.eduardova.jwt.services.impl;

import dev.eduardova.jwt.dtos.users.UserSecurityDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import dev.eduardova.jwt.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usersRepository.findByEmail(email)
            .map(user -> {
                var roles = user.getRoles();
                var authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .toList();
                return new UserSecurityDetails(user.getEmail(), user.getPassword(), authorities, user.getEnabled());
            }).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
    }
}
