package dev.eduardova.jwt.services.impl;

import dev.eduardova.jwt.dtos.roles.RoleRequest;
import dev.eduardova.jwt.dtos.roles.RoleResponse;
import dev.eduardova.jwt.entities.Role;
import dev.eduardova.jwt.exceptions.GeneralInputErrorException;
import dev.eduardova.jwt.repositories.RoleRepository;
import dev.eduardova.jwt.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    @Override
    public RoleResponse add(RoleRequest request) {
        var role = new Role();
        var roleName = request.getName().trim().toUpperCase();

        if (roleName.isEmpty()) {
            throw new GeneralInputErrorException("Role name is required");
        }

        if (!roleName.startsWith("ROLE_") && !roleName.startsWith("role_")) {
            roleName = "ROLE_" + roleName;
        }

        role.setName(roleName);
        var saved = roleRepository.save(role);
        return mapper.map(saved, RoleResponse.class);
    }

    @Override
    public Page<RoleResponse> getAll(Pageable pageable) {
        return roleRepository.findAll(pageable)
            .map(role -> mapper.map(role, RoleResponse.class));
    }

}
