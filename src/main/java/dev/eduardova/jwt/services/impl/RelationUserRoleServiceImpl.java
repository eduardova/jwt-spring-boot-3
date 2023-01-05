package dev.eduardova.jwt.services.impl;

import dev.eduardova.jwt.dtos.relations.userrole.UserRoleResponse;
import dev.eduardova.jwt.dtos.roles.RoleResponse;
import dev.eduardova.jwt.dtos.users.UserResponse;
import dev.eduardova.jwt.exceptions.NotFoundException;
import dev.eduardova.jwt.repositories.RoleRepository;
import dev.eduardova.jwt.repositories.UserRepository;
import dev.eduardova.jwt.services.RelationUserRoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationUserRoleServiceImpl implements RelationUserRoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public void add(Long idUser, Long idRole) {

        var user = userRepository.findById(idUser)
            .orElseThrow(() -> new NotFoundException("User not found"));

        var role = roleRepository.findById(idRole)
            .orElseThrow(() -> new NotFoundException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public Page<UserRoleResponse> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(user -> {
            var roles = user.getRoles();
            var response = new UserRoleResponse();
            response.setUser(mapper.map(user, UserResponse.class));
            response.setRoles(roles.stream().map(role -> mapper.map(role, RoleResponse.class)).toList());
            return response;
        });
    }

}
