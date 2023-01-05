package dev.eduardova.jwt.controllers;

import dev.eduardova.jwt.dtos.roles.RoleRequest;
import dev.eduardova.jwt.dtos.roles.RoleResponse;
import dev.eduardova.jwt.services.RoleService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("roles")
@RolesAllowed("ADMIN")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public Page<RoleResponse> getAll(@PageableDefault Pageable pageable) {
        return roleService.getAll(pageable);
    }

    @PostMapping
    public RoleResponse addRole(@RequestBody RoleRequest request) {
        return roleService.add(request);
    }
}
