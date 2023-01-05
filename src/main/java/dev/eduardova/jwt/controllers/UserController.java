package dev.eduardova.jwt.controllers;

import dev.eduardova.jwt.dtos.users.UserResponse;
import dev.eduardova.jwt.dtos.users.UsersRequest;
import dev.eduardova.jwt.exceptions.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import dev.eduardova.jwt.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@RolesAllowed("ADMIN")
public class UserController {

    private final UserService usersService;
    
    @GetMapping
    public Page<UserResponse> getUsers(@PageableDefault Pageable page) {
        return usersService.getAll(page);
    }

    @PostMapping
    public UserResponse addUser(@RequestBody UsersRequest user) {
        validatePreExisted(user);
        return usersService.add(user);
    }

    private void validatePreExisted(UsersRequest user) throws UserAlreadyExistException {
        var preExist = usersService.exist(user.getEmail());
        if (preExist) {
            throw new UserAlreadyExistException("This email already in use.");
        }
    }

}
