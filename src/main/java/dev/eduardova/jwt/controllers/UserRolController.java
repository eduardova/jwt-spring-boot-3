package dev.eduardova.jwt.controllers;

import dev.eduardova.jwt.dtos.relations.userrole.UserRoleResponse;
import dev.eduardova.jwt.services.RelationUserRoleService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RolesAllowed("ADMIN")
@RequiredArgsConstructor
public class UserRolController {

    private final RelationUserRoleService relationUserRoleService;

    @PostMapping("users/{idUser}/role/{idRole}")
    public void addRelation(@PathVariable Long idUser, @PathVariable Long idRole) {
        relationUserRoleService.add(idUser, idRole);
    }
    
    @GetMapping("users-roles")
    public Page<UserRoleResponse> getAll(@PageableDefault Pageable pageable) {
        return relationUserRoleService.getAll(pageable);
    }

}
