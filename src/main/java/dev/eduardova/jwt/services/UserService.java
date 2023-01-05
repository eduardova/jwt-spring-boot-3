package dev.eduardova.jwt.services;

import dev.eduardova.jwt.dtos.users.UserResponse;
import dev.eduardova.jwt.dtos.users.UsersRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserResponse> getAll(Pageable page);

    UserResponse add(UsersRequest user);

    Boolean exist(String email);
}
