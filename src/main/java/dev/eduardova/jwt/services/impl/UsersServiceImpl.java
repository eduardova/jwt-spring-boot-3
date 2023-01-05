package dev.eduardova.jwt.services.impl;

import dev.eduardova.jwt.dtos.users.UserResponse;
import dev.eduardova.jwt.dtos.users.UsersRequest;
import dev.eduardova.jwt.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import dev.eduardova.jwt.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import dev.eduardova.jwt.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UserService {

    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Override
    public Page<UserResponse> getAll(Pageable page) {
        return usersRepository
            .findAll(page)
            .map(usr -> mapper.map(usr, UserResponse.class));
    }

    @Override
    public Boolean exist(String email) {
        return usersRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserResponse add(UsersRequest userReq) {
        var user = new User();

        user.setFirstName(userReq.getFirstName());
        user.setLastName(userReq.getLastName());
        user.setEmail(userReq.getEmail());
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));
        user.setEnabled(Boolean.TRUE);
        var userSaved = usersRepository.save(user);
        return mapper.map(userSaved, UserResponse.class);
    }
}
